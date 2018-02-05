package be.phw.serv.multitenancy;

import be.phw.serv.security.oauth2.AuthorizationHeaderUtil;

public class TenantUtils {

    public static final String TENANT_PATH_VAR = "{realm}";
    public static final String TENANT_PATH_PREFIX = "realms";

    public String getTenantFromSecu(){
        String token = AuthorizationHeaderUtil.getAuthorizationHeader().get();
        if (token != null){
            return TokenDecoder.getInstance().getTenant(token);
        }
        return null;
    }

}
