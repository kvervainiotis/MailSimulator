# MailSimulator
A mail simulator in java, where you can send and receive mails with other users. Created with MYSQL database.

Τhis project concerns a console application for an email simulator that enables users to send and receive messages. 
There is a code for the creation of a MYSQL database. The mail simulator will start operating after the user runs the creation code. 
An exported database with some users comprised in it, is included to help resolve possible problems with the creation of the database. 
The first log in must be done with the admin username and the admin password, who will then create other users and start the program.
Five different roles for users have been created, each one with different capabilities. 
All users have a profile with their personal inbox and sent messages, which they can send, delete or update.

Super admin can create, delete and update users, as he is the master of the project.
VIP has access to all users’ messages, which he can delete, update, send and receive.
Super user can only delete all users‘ messages as well as his own messages.
Power user can only view all users’ messages and CRUD his own messages. 
User can only send and receive messages and have access only to his own messages. 
All messages are stored in txt files after they are sent. When a user deletes a message, he can no longer view it whereas other users can. 
If the other participant of that message deletes it too, the message is then deleted from the database and no one can access it from that moment on.

Program functions:
*My profile: Offers a simple user the possibility to receive, delete and update personal messages.
*Lobby: Contains all the messages of all the users. However not all users have permission to enter this section.
*Manage database: Assembles all super admin’s functions : create and update all program’s users, backup all messages to a text file.

MUSQL database: Consists of six tables:
*Users: Comprises users’ data with PK the id field.
*Roles: Includes the roles of the program. Each user has a role from this table.
*Message: Contains all the messages sent from the users. It includes the data, the message id and the date of the message, as well as the date of its update, if there is one.
*Send_message: Contains information about the sent messages, such as the sender’s and the receiver’s ID, the ID of the message and a delete column. 
The delete is 0. If one of the two participants deletes the message, the column takes the ID of the user that deleted it. 
If the second participant deletes it too, then the message is deleted from the database.
