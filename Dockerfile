# Use official Tomcat image
FROM tomcat:9.0

# Copy WAR file into Tomcat webapps directory
COPY target/MathematicsVideoPlatform.war /usr/local/tomcat/webapps/

# Expose port 8080 for web traffic
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
