# ChatSpark

ChatSpark is a simple peer-to-peer messaging server where a user can be registered with a unique human-readable username and they can start message threads with one another.
The server should also fetch the chat history requested by the user. The user should be able to send a message to multiple users in once.

## API Endpoints requirements:
1. Creating a user on the server: Build REST API `/create/user`.
2. Logging into the user session: Build a REST API for authorizing the user. A user can only perform send and receive if he/she is logged in.
3. Fetching Users on the server: Build an API to get the users on the server.
4. Fetch unread messages: Build REST API to fetch a user’s unread messages.
5. Send a new message: API to send a specific user a message.
6. Get chat history with a specific user.
7. Logout of the current client session.

## Requirements & steps to run the code:

1. Java 17 with Maven to build the project.
2. Required dependencies are already added in `pom.xml`.
3. Run the `ChatApplication.java` to start the application. The server will start on port 8080.
4. Run the endpoints present in `MessageController.java` via Postman. The Postman collection is added below. Copy the below json and import it in Postman.
   
## Note:
1. Data are stored in memory using Map data structure, which can be replaced with any RDMS/NoSQL database for storing users and chat details.
2. WebSecurityConfiguration is done to filter endpoints starting with `/chat-spark/**`. Changing that will throw 401 error code.
3. 

```
{
	"info": {
		"_postman_id": "41c24bcf-38d7-4550-9d15-5b3d409918f6",
		"name": "Chat Spark",
		"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json",
		"_exporter_id": "19077812"
	},
	"item": [
		{
			"name": "Create User",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n  \"username\": \"johnsmith007\",\n  \"passcode\": \"wawa009\"\n}"
				},
				"url": {
					"raw": "http://localhost:8080/chat-spark/create/user",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"chat-spark",
						"create",
						"user"
					]
				}
			},
			"response": []
		},
		{
			"name": "Login session",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n  \"username\": \"dummy008\",\n  \"passcode\": \"cocopuf\"\n}"
				},
				"url": {
					"raw": "http://localhost:8080/chat-spark/login",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"chat-spark",
						"login"
					]
				}
			},
			"response": []
		},
		{
			"name": "Get User Details",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "http://localhost:8080/chat-spark/get/users?username=johnsmith007",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"chat-spark",
						"get",
						"users"
					],
					"query": [
						{
							"key": "username",
							"value": "johnsmith007"
						}
					]
				}
			},
			"response": []
		},
		{
			"name": "Read Unread messages",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "http://localhost:8080/chat-spark/get/unread?username=dummy009",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"chat-spark",
						"get",
						"unread"
					],
					"query": [
						{
							"key": "username",
							"value": "dummy009"
						}
					]
				}
			},
			"response": []
		},
		{
			"name": "Send text to a user",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n  \"from\": \"johnsmith007\",\n  \"to\": \"dummy009\",\n  \"text\": \"More unread 5\"\n}"
				},
				"url": {
					"raw": "http://localhost:8080/chat-spark/send/text/user",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"chat-spark",
						"send",
						"text",
						"user"
					]
				}
			},
			"response": []
		},
		{
			"name": "Get Chat history of a user",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "http://localhost:8080/chat-spark/get/history?user=dummy008&friend=johnsmith007",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"chat-spark",
						"get",
						"history"
					],
					"query": [
						{
							"key": "user",
							"value": "dummy008"
						},
						{
							"key": "friend",
							"value": "johnsmith007"
						}
					]
				}
			},
			"response": []
		},
		{
			"name": "Logout of the session ",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n  \"username\": \"johnsmith007\"\n}"
				},
				"url": {
					"raw": "http://localhost:8080/chat-spark/logout",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"chat-spark",
						"logout"
					]
				}
			},
			"response": []
		},
		{
			"name": "Send message to a group of users",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n  \"sender\": \"johnsmith007\",\n  \"recipients\": [\"dummy009\", \"dummy008\"],\n  \"text\": \"Hello, everyone! How are you doing?\"\n}"
				},
				"url": {
					"raw": "http://localhost:8080/chat-spark/send/text/group",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"chat-spark",
						"send",
						"text",
						"group"
					]
				}
			},
			"response": []
		},
		{
			"name": "Block user",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json"
					},
					{
						"key": "Cookie",
						"value": "JSESSIONID=832497EC926617392DCD388E336F52FC"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n  \"blockedBy\": \"johnsmith007\",\n  \"blockedTo\": \"\"\n}"
				},
				"url": {
					"raw": "http://localhost:8080/chat-spark/block/user",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"chat-spark",
						"block",
						"user"
					]
				}
			},
			"response": []
		}
	]
}
```
