echo Running keytool generate cert for developer testing - Not for production use.

rm src/main/resources/auth-public-cert.pem
rm ~/certificates/ftl/demo-realm/auth-public-cert.pem
rm src/main/resources/keystore-auth.p12

keytool -alias root -dname "cn=RootCA, ou=Root_CertificateAuthority, o=CertificateAuthority, c=IN" -genkeypair -storepass "3q@v)dha{M:7A{8?" -keyalg RSA -storetype PKCS12 -keystore "src/main/resources/keystore-auth.p12"

keytool -alias intermediate -dname "cn=IntermediateCA, ou=Intermediate_CertificateAuthority, o=CertificateAuthority, c=IN" -genkeypair -keystore "src/main/resources/keystore-auth.p12" -storepass "3q@v)dha{M:7A{8?" -keyalg RSA

keytool -genkeypair -alias auth-server -keyalg RSA -keysize 4096 -storetype PKCS12 -keystore src/main/resources/keystore-auth.p12 -validity 3650 -storepass "3q@v)dha{M:7A{8?" -dname "CN=localhost" -ext "SAN=IP:127.0.0.1,IP:192.168.76.42,IP:192.168.76.43,IP:192.168.76.44,IP:192.168.1.128,DNS:messaging1.lxc,DNS:messaging2.lxc,DNS:messaging3.lxc"

keytool -export -alias auth-server -storepass "3q@v)dha{M:7A{8?" -keystore src/main/resources/keystore-auth.p12 -rfc -file src/main/resources/auth-public-cert.pem

keytool -export -alias root -storepass "3q@v)dha{M:7A{8?" -keystore src/main/resources/keystore-auth.p12 -rfc -file src/main/resources/auth-root-cert.pem

keytool -alias intermediate -certreq -genkeypair -keystore "src/main/resources/keystore-auth.p12" -storepass "3q@v)dha{M:7A{8?" -keyalg RSA | keytool -alias root -gencert -ext san=dns:intermediate -genkeypair -keystore "src/main/resources/keystore-auth.p12" -storepass "3q@v)dha{M:7A{8?" -keyalg RSA | keytool -alias intermediate -importcert -genkeypair -keystore "src/main/resources/keystore-auth.p12" -storepass "3q@v)dha{M:7A{8?" -keyalg RSA

keytool -export -alias root -keystore "src/main/resources/keystore-auth.p12" -storepass "3q@v)dha{M:7A{8?" | keytool -import -alias root -keystore "src/main/resources/keystore-identity.p12" -storepass "3q@v)dha{M:7A{8?" -noprompt -trustcacerts

keytool -alias auth-server -certreq -keystore "src/main/resources/keystore-identity.p12" -storepass "3q@v)dha{M:7A{8?" -keyalg RSA  | keytool -alias intermediate -gencert -storepass password -keyalg RSA | keytool -alias auth-server -importcert -keyalg RSA -keystore "src/main/resources/keystore-identity.p12" -storepass "3q@v)dha{M:7A{8?" -noprompt -trustcacerts

keytool -list -storepass "3q@v)dha{M:7A{8?" -keystore src/main/resources/keystore-auth.p12 -rfc

keytool -list -storepass "3q@v)dha{M:7A{8?" -keystore src/main/resources/keystore-identity.p12 -rfc

cp src/main/resources/auth-public-cert.pem ~/certificates/ftl/demo-realm/auth-public-cert.pem

