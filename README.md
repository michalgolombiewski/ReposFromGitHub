# GitHubRepos

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

## General info
This project is a simple Rest Api app collects github repositories according to below acceptance criteria:<br>
As an api consumer, given username and header “Accept: application/json”, I would like to list all his github repositories, which are not forks. Information, which I require in the response, is:<br><br>

Repository Name<br>
Owner Login<br>
For each branch it’s name and last commit sha<br><br>

As an api consumer, given not existing github user, I would like to receive 404 response in such a format:<br>
<pre>{
    “status”: ${responseCode}
    “Message”: ${whyHasItHappened}
}</pre>

As an api consumer, given header “Accept: application/xml”, I would like to receive 406 response in such a format:<br>
<pre>{
    “status”: ${responseCode}
    “Message”: ${whyHasItHappened}
}</pre>

## Technologies
Project is created with:
* Java version 17
* SpringBoot 3

## Setup
To run this project please download this repositorium and run it in your IDE
Put in a web browser http://localhost:8080/repos/<gitUser>
You should also put you PAT (Personal Acces Token) from your GitHub account.
The Token put in GithubService class in 
<pre>
private static final String TOKEN = "<TOKEN>";
</pre>
  
