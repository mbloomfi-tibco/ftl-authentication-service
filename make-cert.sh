echo Running keytool generate cert for developer testing - Not for production use.

echo "Generate public and root certificates in p12 store"
rm src/main/resources/keystore-auth.p12
rm src/main/resources/keystore-identity.p12
keytool -alias root -dname "cn=RootCA, ou=Root_CertificateAuthority, o=CertificateAuthority, c=IN" -genkeypair -storepass "3q@v)dha{M:7A{8?" -keyalg RSA -storetype PKCS12 -keystore "src/main/resources/keystore-auth.p12"
keytool -alias intermediate -dname "cn=IntermediateCA, ou=Intermediate_CertificateAuthority, o=CertificateAuthority, c=IN" -genkeypair -keystore "src/main/resources/keystore-auth.p12" -storepass "3q@v)dha{M:7A{8?" -keyalg RSA
keytool -genkeypair -alias auth-server -keyalg RSA -keysize 4096 -storetype PKCS12 -keystore src/main/resources/keystore-auth.p12 -validity 3650 -storepass "3q@v)dha{M:7A{8?" -dname "CN=localhost" -ext "SAN=IP:127.0.0.1,IP:192.168.76.42,IP:192.168.76.43,IP:192.168.76.44,IP:192.168.1.128,DNS:messaging1.lxc,DNS:messaging2.lxc,DNS:messaging3.lxc"
keytool -alias intermediate -certreq -genkeypair -keystore "src/main/resources/keystore-auth.p12" -storepass "3q@v)dha{M:7A{8?" -keyalg RSA | keytool -alias root -gencert -ext san=dns:intermediate -genkeypair -keystore "src/main/resources/keystore-auth.p12" -storepass "3q@v)dha{M:7A{8?" -keyalg RSA | keytool -alias intermediate -importcert -genkeypair -keystore "src/main/resources/keystore-auth.p12" -storepass "3q@v)dha{M:7A{8?" -keyalg RSA
keytool -alias auth-server -certreq -keystore "src/main/resources/keystore-identity.p12" -storepass "3q@v)dha{M:7A{8?" -keyalg RSA  | keytool -alias intermediate -gencert -storepass password -keyalg RSA | keytool -alias auth-server -importcert -keyalg RSA -keystore "src/main/resources/keystore-identity.p12" -storepass "3q@v)dha{M:7A{8?" -noprompt -trustcacerts

echo "Export public and root certificates"
rm src/main/resources/public-cert.pem
rm src/main/resources/root-cert.pem
rm src/main/resources/auth-public-cert.pem

keytool -export -alias auth-server -storepass "3q@v)dha{M:7A{8?" -keystore src/main/resources/keystore-auth.p12 -rfc -file client-certificate/public-cert.pem
keytool -export -alias root -storepass "3q@v)dha{M:7A{8?" -keystore src/main/resources/keystore-auth.p12 -rfc -file client-certificate/root-cert.pem
keytool -export -alias root -keystore "src/main/resources/keystore-auth.p12" -storepass "3q@v)dha{M:7A{8?" | keytool -import -alias root -keystore "src/main/resources/keystore-identity.p12" -storepass "3q@v)dha{M:7A{8?" -noprompt -trustcacerts

echo "combining root and public certificate into one pem file"
cat client-certificate/root-cert.pem client-certificate/public-cert.pem >> client-certificate/auth-public-cert.pem

echo "Validate the keystores by listing certificates"
keytool -list -storepass "3q@v)dha{M:7A{8?" -keystore src/main/resources/keystore-auth.p12 -rfc
keytool -list -storepass "3q@v)dha{M:7A{8?" -keystore src/main/resources/keystore-identity.p12 -rfc

echo "Copy client certificate for FTL to deployment directory"
rm ~/certificates/ftl/demo-realm/auth-public-cert.pem
cp client-certificate/auth-public-cert.pem ~/certificates/ftl/demo-realm/auth-public-cert.pem