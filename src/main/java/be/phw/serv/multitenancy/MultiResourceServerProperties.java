package be.phw.serv.multitenancy;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;

import java.io.Serializable;
import java.util.Optional;

public class MultiResourceServerProperties extends ResourceServerProperties implements Serializable{

    @Override
    public String getUserInfoUri() {
        String uri = super.getUserInfoUri().replace(TenantUtils.TENANT_PATH_VAR, getTenant());
        System.out.println("UserInfoUri " + uri);
        return uri;
    }

    @Override
    public String getTokenInfoUri() {
        String uri = super.getTokenInfoUri().replace(TenantUtils.TENANT_PATH_VAR, getTenant());
        System.out.println("TokenInfoUri " + uri);
        return uri;
    }

    @Override
    public Jwt getJwt() {
        Jwt jwt = super.getJwt();
        if (jwt != null && jwt.getKeyUri() != null){
            jwt.setKeyUri(jwt.getKeyUri().replace(TenantUtils.TENANT_PATH_VAR, getTenant()));
        }
        return jwt;
    }

    private String getTenant(){
        //get selected tenant
        //TODO : rechercher dans context ou de la secu
        return Optional.ofNullable(TenantContext.getCurrentTenant()).orElse(TenantUtils.TENANT_PATH_VAR);
    }
}
