ComandesOnline
==============
-Esta provat amb tomcat 6.0.32

//Han d'anar al catalina.policy 
  	permission java.io.FilePermission "META-INF/wink-default.properties", "read";   
    permission java.io.FilePermission "META-INF/wink-alternate-shortcuts.properties", "read";   
    permission java.io.FilePermission "META-INF/core/wink-providers", "read";   
    permission java.io.FilePermission "META-INF/server/wink-providers", "read";   
    permission java.lang.reflect.ReflectPermission "suppressAccessChecks";