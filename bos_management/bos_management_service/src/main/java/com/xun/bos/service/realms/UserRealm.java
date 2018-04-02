package com.xun.bos.service.realms;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xun.bos.dao.system.PermissionRepository;
import com.xun.bos.dao.system.RoleRepository;
import com.xun.bos.dao.system.UserRepository;
import com.xun.bos.domain.system.Permission;
import com.xun.bos.domain.system.Role;
import com.xun.bos.domain.system.User;

/**  
 * ClassName:UserRealm <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午3:50:29 <br/>       
 */
@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RoleRepository roleRepository;
    
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if ("admin".equals(user.getUsername())) {
            
            List<Role> roles = roleRepository.findAll();
            for (Role role : roles) {
                info.addRole(role.getKeyword());
            }
            List<Permission> permissions = permissionRepository.findAll();
            for (Permission permission : permissions) {
                info.addStringPermission(permission.getKeyword());
            }
            
        }else {
            List<Role> roles = roleRepository.findbyUser(user.getId());
            for (Role role : roles) {
                info.addRole(role.getKeyword());
            }
            List<Permission> permissions = permissionRepository.findbyUser(user.getId());
            for (Permission permission : permissions) {
                info.addStringPermission(permission.getKeyword());
            }
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        User user = userRepository.findByUsername(username);
        if (user != null) {
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
            return authenticationInfo;
        }
        return null;
    }

}
  
