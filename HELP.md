## Preparing Authentication Service 

1) Generate a private root and server certificate in a p12 certificate store using the make-cert.sh script or provide your own p12 store and place it in the projects resources folder for development purposes.  

If you want to externalise the store then change the location, currently set as follows,  key-store: classpath:keystore-auth.p12, to point to the external store.   

If you also used a different password for the store then this also needs to be changed in the application.yml properties file.

2) Out of the p12 store generate a pem file with Public and Root certificate

Export the public and root certificate from the p12 store in PEM format and combine to make one pem file. 
This PEM file needs to be loaded to each FTL server. 

## Preparing FTL Server

1) Generate Certificates for FTL by either using tibftlserver --init-security or tibftlserver --init-auth-only commands.
   More information can be found in the TIBCO FTL security guide https://docs.tibco.com/products/tibco-ftl-enterprise-edition-6-8-1

3) Update the FTL server Yaml file with the following global properties

   auth.url: "https://[Location of Authentication Service]:443/login"
   auth.trust: "[Location of certificates directory]/auth-public-cert.pem"

   Note:  There are also username and password available for plain authentication with an authentication service.  This example does not include authentication of the FTL server at this stage but may be added in future or can be extended to do so using Spring Framework.

   auth.user: "Franky"
   auth.password: "FrankyFish"

4) Start / Restart the server

## Information on LDAP Client

The LDAP client uses simple naming javax.naming to authenticate and authorize users.  Currently the values for LDAP are hard coded in the SimpleLDAPClient.  In the next version this will be configurable.

## Spring Boot LDAP
To use spring boot LDAP these articles may be off help.  The examples on focus LDAP for authenticating http services which is not possible to do from FTL
  
https://www.baeldung.com/spring-ldap
https://github.com/eugenp/tutorials/tree/master/spring-security-modules/spring-security-ldap

