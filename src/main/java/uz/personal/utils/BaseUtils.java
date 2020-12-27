package uz.personal.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.personal.config.ApplicationContextProvider;
import uz.personal.domain.Auditable;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

@Component
public class BaseUtils {

    private static final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();
    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"};

    @Autowired
    private ObjectMapper objectMapper;

    private static String toHex(byte[] data) {
        char[] chars = new char[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            chars[i * 2] = HEX_DIGITS[(data[i] >> 4) & 0xf];
            chars[i * 2 + 1] = HEX_DIGITS[data[i] & 0xf];
        }
        return new String(chars);
    }

    public static String defineMacAddress() {
        InetAddress ip;
        StringBuilder sb = new StringBuilder();
        try {

            ip = InetAddress.getLocalHost();
            System.out.println("Current IP address : " + ip.getHostAddress());

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            byte[] mac = network.getHardwareAddress();

            System.out.print("Current MAC address : ");

            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            System.out.println(sb.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return sb.toString();
    }

    public static JsonNode stringToJsonNode(String data) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readTree(data);
        } catch (IOException e) {
            throw new RuntimeException("could not parse string data");
        }
    }

    public static Object getBean(String name) {
        return ApplicationContextProvider.applicationContext.getBean(name);
    }

    public String getClientIpAddress(HttpServletRequest request) {
        for (String header : IP_HEADER_CANDIDATES) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

    public boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public boolean isEmpty(List<?> items) {
        return items == null || items.isEmpty();
    }

    public boolean isEmpty(Object l) {
        return l == null;
    }

    public static void error(Logger log, Exception e) {
        if (e.getCause() == null)
            log.error(e.getStackTrace(), e);
        else
            log.error(e.getStackTrace(), e.getCause());
    }

    public String toErrorParams(Object... args) {
        StringBuilder builder = new StringBuilder();
        Arrays.asList(args).forEach(t -> builder.append("#").append(toStringErrorParam(t)));
        return builder.toString().substring(1);
    }

    private String toStringErrorParam(Object argument) {
        if (argument instanceof Auditable) {
            return argument.getClass().getSimpleName();
        }
        return argument.toString();
    }

    public String encodeToMd5(String data) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes(), 0, data.length());
            return toHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String encodeToBase64(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes());
    }

    public JsonNode fromStringToNode(String data) {
        try {
            return objectMapper.readTree(data);
        } catch (IOException e) {
            throw new RuntimeException("could not parse string data");
        }
    }

    public HashMap<String, JsonNode> fromJsonToHashMap(JsonNode jsonNode) throws IOException {
        return objectMapper.readValue(jsonNode.traverse(), new TypeReference<HashMap<String, JsonNode>>() {
        });
    }

    public HashMap<String, JsonNode> fromStringToHashMap(String data) throws IOException {
        return fromJsonToHashMap(fromStringToNode(data));
    }

    public boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public Object nodeToObject(JsonNode node, Field field) throws JsonProcessingException {
        return objectMapper.treeToValue(node, field.getType());
    }
}
