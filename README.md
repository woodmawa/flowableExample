Simple demo Flowable project 

create a system environment variable FLOWABLE_ADMIN_PASSWORD - which the project will read when creating the admin user in the in memory Spring Security context 
app is secured using basic auth onlt (admin/{env var}) as its demo only 
Warning: Do not to be use in production - example flowable project only 

This is gradle build project so you can clone and build locally from the command line or using your favourite IDE
clone/zip download the project to a local directory of your choice, and cd into that 
you can run the gradle build using gradle wrapper (gradlew build : Linux or gradlew.bat build: Windows) which should sort out and grab gradle and run the build 

this is springboot project - but i've added so basic rest api endpoints ( demo only ) for some basic things 

gradlew run - should run the code 

i've attached postman collection as well into the project if you want to use postman to try it out 

There are tree default processes that will auto deploy on startup, one for a script process ( groovy 'hello <name>' task) , another for same using java delegate call to compiled class, and one that has a simple user task

easiest one to to shoot for is post http://localhost:8080/api/process/start?name=simple-script-task&variable=Will   which will say hello with your name.  this returns the process id, which you can copy paste into the get process variables get action http://localhost:8080/api/process/variables?pid=<pastehere>

actuator endpoint is here http://localhost:8080/actuator/flowable

lots of places this could go - to productionise it, but shows the basic idea with a couple of sample simple processes 

there is a demo online bpmn editor if you just want to 'play' with designing a process.  I create a bpmn file upload and deploy endpoint - but have test that.

demo bpmn editor online you can play with include https://demo.bpmn.io/, others as per google searches 




