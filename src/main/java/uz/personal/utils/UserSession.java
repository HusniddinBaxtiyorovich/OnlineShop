package uz.personal.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.personal.domain.auth._User;
import uz.personal.dto.auth.CustomUserDetails;
import uz.personal.dto.auth.UserDto;
import uz.personal.enums.Headers;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserSession {

    HttpServletRequest request;
    BaseUtils utils;

    @Autowired
    public UserSession(HttpServletRequest request, BaseUtils utils) {
        this.request = request;
        this.utils = utils;
    }

    public UserDto getUser() {
        UserDto user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof _User) {
                user = (UserDto) authentication.getPrincipal();
            }
            if (authentication.getPrincipal() instanceof CustomUserDetails) {
                user = ((CustomUserDetails) authentication.getPrincipal()).getUserDto();
            }
        }
        return user;
    }

    public _User getDBUser() {
        _User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof _User) {
                user = (_User) authentication.getPrincipal();
            }
            if (authentication.getPrincipal() instanceof CustomUserDetails) {
                user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
            }
        }
        return user;
    }

    public String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Map<Headers, String> getHeadersInfo() {
        HashMap<String, String> map = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return convertHeader(map);
    }

    public String getLanguage() {
        Map<Headers, String> headers = getHeadersInfo();
        if (headers.containsKey((Headers.LANGUAGE))) {
            return headers.get(Headers.LANGUAGE);
        } else {
            return Headers.LANGUAGE.defValue;
        }
    }

    private HashMap<Headers, String> convertHeader(HashMap<String, String> map) {
        HashMap<Headers, String> hashMap = new HashMap<>();
        for (String s : map.keySet()) {
            if (Headers.findByKey(s) != null) {
                hashMap.put(Headers.findByKey(s), map.get(s));
            }
        }
        return hashMap;
    }
}
