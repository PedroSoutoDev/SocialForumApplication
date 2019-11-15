# Forum Backend - Read Only
Fall 2019 class group project. Creating the backend for a read-only web application representing a forum.

### ResponseDto
By using a Data Transfer Object (DTO), communication between the web application and the backend server is cheaper and faster. By converting it back and forth between a JSON, this object can be easily sent via HTTP calls. It also allows the web application to display its content easily. Finally, with a standardized response it is trivial to add a frontend to this project.

### PostDto & UserDto
Classes used to store users and posts on the running server. As users and posts are scanned from a URL, they are converted into *DTO objects* that are easy to work with. This makes it possible to search, sort, print, remove, and add new entries to both the users and posts list.

### PostDao & UserDao
By using a data access object (DAO), the server has an interface by which to access posts and users. Due to the fact that the post and user objects are stored on the server (as opposed to a database), accessing a specific post or user is as simple as searching an array.

### ProcessorFactory
When a user access the web application, a call referring to *either* user **or** posts is made. This call is then sent to a processor, where to correct reponse is built then sent back to the user. Due to the fact that both the processors have a process() method, a factory builder pattern is perfect. The factory takes in the user URL as a parameter, and returns the correct processor class.

### PostProcessor & UserProcessor
The processors are the logic of the server. Once the correct processor is made via the processor factory, the newly made processor can parse the given URL to produce a response object. The processor is reponsible for tokenizing and parsing the URL parameters.

For instance, a call to *htt<i></i>p://localhost:1299/users*  
Returns: 
<pre>
{
  "date": "Nov 15, 2019 1:19:25 AM",
  "responseCode": "OK",
  "response": [
    {
      "username": "rey",
      "userid": 0
    },
    {
      "username": "lukeSkywalker",
      "userid": 1
    },
    {
      "username": "daenerysStormborn",
      "userid": 2
    },
    {
      "username": "khalDrogo",
      "userid": 3
    }
  ]
}
</pre>

While a call to *htt<i></i>p://localhost:1299/users?userid=2*  
Returns:  
<pre>
{
 "date": "Nov 15, 2019 1:19:25 AM",
 "responseCode": "OK",
 "response": [ 
   {
     "username": "daenerysStormborn",
     "userid": 2
   }
 ]
}
</pre>
