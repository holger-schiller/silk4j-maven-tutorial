## Maven - Silk4J test project

- see https://community.microfocus.com/devops-cloud/b/devops-blog/posts/embracing-open-source-maven-and-silk4j

- especially section to install Silk4J jars into maven repository
    - Note: change version from 16.5 to 21.0.2
	
- ignore section to use Keywords  

- folder / project : silk4j.maven.demo

- silk4j.settings  
    - you can replace "GoogleChrome" with other browsers like Firefox, ...
    - change "OPT_ASSET_NAMESPACE" value to reflect the project / folder name (see above)

- folder "Object Maps" as source folder added
    - maven didn't find resources out-side standard maven path
    - Silk4J didn't find "Object Maps" in test resource folder 

- run it  
  ~~~  
  mvn test  
  ~~~   
