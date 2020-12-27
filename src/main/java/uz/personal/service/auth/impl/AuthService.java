package uz.personal.service.auth.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.oauth2.sdk.GrantType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.stereotype.Service;
import uz.personal.domain.auth._User;
import uz.personal.dto.auth.*;
import uz.personal.enums.ErrorCodes;
import uz.personal.enums.UserType;
import uz.personal.exception.IdRequiredException;
import uz.personal.mapper.auth.UserMapper;
import uz.personal.repository.auth.IUserRepository;
import uz.personal.repository.setting.IErrorRepository;
import uz.personal.response.AppErrorDto;
import uz.personal.response.DataDto;
import uz.personal.service.auth.IAuthService;
import uz.personal.utils.BaseUtils;
import uz.personal.utils.UserSession;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AuthService implements IAuthService {

    /**
     * Common logger for use in subclasses.
     */
    private final Log logger = LogFactory.getLog(getClass());
    private final IErrorRepository errorRepository;
    private final UserMapper userMapper;
    private final BaseUtils utils;
    private final IUserRepository userRepository;
    private final PasswordEncoder userPasswordEncoder;
    private final UserSession userSession;

    @Resource(name = "tokenServices")
    ConsumerTokenServices tokenServices;

    @Value("${oauth2.clientId}")
    private String clientId;

    @Value("${oauth2.clientSecret}")
    private String clientSecret;

    @Value("${server.oauth.url}")
    private String OAUTH_AUTH_URL;

    @Autowired
    public AuthService(IErrorRepository errorRepository, UserMapper userMapper, BaseUtils utils, IUserRepository userRepository, PasswordEncoder userPasswordEncoder, UserSession userSession) {
        this.errorRepository = errorRepository;
        this.userMapper = userMapper;
        this.utils = utils;
        this.userRepository = userRepository;
        this.userPasswordEncoder = userPasswordEncoder;
        this.userSession = userSession;
    }

    @Override
    public ResponseEntity<DataDto<SessionDto>> login(AuthUserDto user, HttpServletRequest request) {
        if (utils.isEmpty(user.getUsername()))
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_GIVEN_FIELD_REQUIRED, utils.toErrorParams("username", "User")));
        if (utils.isEmpty(user.getPassword()))
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_GIVEN_FIELD_REQUIRED, utils.toErrorParams("password", "User")));

        user.setUsername(user.getUsername().toLowerCase());

        try {
            HttpClient httpclient = HttpClientBuilder.create().build();
            HttpPost httppost = new HttpPost(OAUTH_AUTH_URL);
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("grant_type", GrantType.PASSWORD.getValue()));
            nameValuePairs.add(new BasicNameValuePair("username", user.getUsername()));
            nameValuePairs.add(new BasicNameValuePair("password", user.getPassword()));


            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httppost.addHeader(HttpHeaders.AUTHORIZATION, getAuthorization());
            httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            HttpResponse response = httpclient.execute(httppost);
            return getAuthDtoDataDto(user, request, response);

        } catch (Exception ex) {
            return new ResponseEntity<>(new DataDto<>(new AppErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<DataDto<Boolean>> logout(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.contains("Bearer")) {
            String tokenId = authorization.substring("Bearer".length() + 1);
            tokenServices.revokeToken(tokenId);
            return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
        }
        return new ResponseEntity<>(new DataDto<>(false), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DataDto<SessionDto>> refreshToken(AuthUserRefreshTokenDto user, HttpServletRequest request) {
        try {
            HttpClient httpclient = HttpClientBuilder.create().build();
            HttpPost httppost = new HttpPost(OAUTH_AUTH_URL);

            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("grant_type", GrantType.REFRESH_TOKEN.getValue()));
            nameValuePairs.add(new BasicNameValuePair("refresh_token", user.getRefreshToken()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            httppost.addHeader(HttpHeaders.AUTHORIZATION, getAuthorization());
            httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            HttpResponse response = httpclient.execute(httppost);

            JsonNode json_auth = new ObjectMapper().readTree(EntityUtils.toString(response.getEntity()));

            SessionDto sessionDto = SessionDto.builder()
                    .expiresIn(json_auth.get("expires_in").asLong())
                    .sessionToken(json_auth.get("access_token").asText())
                    .refreshToken(json_auth.get("refresh_token").asText())
                    .build();

            return new ResponseEntity<>(new DataDto<>(sessionDto), HttpStatus.OK);

        } catch (IOException ex) {
            return new ResponseEntity<>(new DataDto<>(new AppErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage())), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<DataDto<Boolean>> changePassword(ChangePasswordDto dto) {
        if (utils.isEmpty(dto.getCurrentPassword()))
            throw new IdRequiredException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_GIVEN_FIELD_REQUIRED, utils.toErrorParams("currentPassword", "User")));
        if (utils.isEmpty(dto.getNewPassword()))
            throw new IdRequiredException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_GIVEN_FIELD_REQUIRED, utils.toErrorParams("newPassword", "User")));

        if (!userPasswordEncoder.matches(dto.getCurrentPassword(), userSession.getDBUser().getPassword())) {
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.PASSWORD_INCORRECT, ""));
        }

        _User user = userSession.getDBUser();
        if (utils.isEmpty(user)) {
            logger.error("Current User not found");
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_NOT_FOUND, "User"));
        }

        user.setPassword(new BCryptPasswordEncoder().encode(dto.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(new DataDto<>(true));
    }

    @Override
    public ResponseEntity<DataDto<Boolean>> resetPassword(ResetPasswordDto dto) {
        if (utils.isEmpty(dto.getUserId()))
            throw new IdRequiredException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_ID_REQUIRED, "User"));
        if (utils.isEmpty(dto.getPassword()))
            throw new IdRequiredException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_NOT_NULL, "password"));

        _User user = userRepository.find(dto.getUserId());
        if (utils.isEmpty(user)) {
            logger.error(String.format("User with id '%s' not found", dto.getUserId()));
            throw new uz.personal.exception.ValidationException(errorRepository.getErrorMessage(ErrorCodes.USER_NOT_FOUND_ID, "" + dto.getUserId()));
        }

        if (UserType.ADMIN != userSession.getDBUser().getUserType()) {
            if (!Objects.equals(dto.getUserId(), userSession.getUser().getId())) {
                throw new uz.personal.exception.ValidationException(errorRepository.getErrorMessage(ErrorCodes.ACCESS_DENIED, "userType"));
            }
        }

        user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(new DataDto<>(true));
    }

    private ResponseEntity<DataDto<SessionDto>> getAuthDtoDataDto(AuthUserDto user, HttpServletRequest request, HttpResponse response) throws IOException {

        JsonNode json_auth = new ObjectMapper().readTree(EntityUtils.toString(response.getEntity()));

        if (!json_auth.has("error")) {
            SessionDto authDto = SessionDto.builder()
                    .expiresIn(json_auth.get("expires_in").asLong())
                    .sessionToken(json_auth.get("access_token").asText())
                    .refreshToken(json_auth.get("refresh_token").asText())
                    .user(userMapper.toUserMeDto(userRepository.findByUsername(user.getUsername().toLowerCase()))).build();

            return new ResponseEntity<>(new DataDto<>(authDto), HttpStatus.OK);
        } else {
            String error_description = json_auth.has("error_description") ? json_auth.get("error_description").asText() : null;
            if (error_description == null || error_description.isEmpty()) {
                error_description = "Username or password is wrong custom message";
            } else if (error_description.contains("NoResultException")) {
                error_description = "Username or password is wrong or this user is not active in this System";
            }
            if (error_description.equals("USER_NOT_FOUND")) {
                return new ResponseEntity<>(new DataDto<>(new AppErrorDto(HttpStatus.NOT_FOUND.value(), error_description)), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(new DataDto<>(new AppErrorDto(response.getStatusLine().getStatusCode(), error_description)), HttpStatus.valueOf(response.getStatusLine().getStatusCode()));
        }
    }

    private String getAuthorization() {
        return "Basic " + utils.encodeToBase64(clientId + ":" + clientSecret);
    }
}
