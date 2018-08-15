import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.*;


import javax.naming.NamingEnumeration;

public class Ldap {

    public static void main(String[] args) throws NamingException {
        String username = "itadmin@domain.local";
        String password = "123456789";
        String url = "ldap://xxx.xxx.xxx.xxx:389/";
        String base = "OU=User,OU=Development,DC=Develop,DC=local";

        Hashtable<String, Object> ldapParams = new Hashtable<String, Object>();
        ldapParams.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        ldapParams.put(Context.PROVIDER_URL, url);
        ldapParams.put(Context.SECURITY_AUTHENTICATION, "simple");
        ldapParams.put(Context.SECURITY_PRINCIPAL, username);
        ldapParams.put(Context.SECURITY_CREDENTIALS, password);

        // Specify SSL
        //ldapParams.put(Context.SECURITY_PROTOCOL, "ssl");

        InitialDirContext ldapCtx = null;

        try {
              ldapCtx = new InitialDirContext(ldapParams);
              System.out.println(ldapCtx);
              if (ldapCtx != null) {
                  System.out.println("login success.");
                  String searchFilter = "(cn=itadmin)";

              SearchControls controls = new SearchControls();
              controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
              NamingEnumeration<SearchResult> results = ldapCtx.search(base, searchFilter, controls);
        
              while (results.hasMore()) {
                SearchResult searchResult = (SearchResult) results.next();
                Attributes attributes = searchResult.getAttributes();
                Attribute attr = attributes.get("cn");
                String cn = (String) attr.get();
                System.out.println(" Person Common Name = " + cn);
              }
            }
        } catch (AuthenticationException ex) {
            System.out.println("login fail. [err 1]");
            System.err.println(ex);
        } catch (NamingException ex) {
            System.out.println("login fail. [err 2]");
            System.err.println(ex);
        } catch (Exception e) {
            System.out.println("login fail. [err 3]");
            System.err.println(e);
        } finally {
            System.out.println("LDAP Context is " + ldapCtx);
        }

    }

}
