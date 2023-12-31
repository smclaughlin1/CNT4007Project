# CNT4007Project

### Description
This is a P2P File sharing Project. The protocol invloves a handshake followed by a 
continuous stream of length-prefixed messages, all assumed to use the TCP Transport Protocol.
Among its interesting features, our Team has implemented the chocking-unchoking mechanisms which is the most important features 
of the P2P file sharing protocol. When the program is run and a peer is connected to at least one other peer, it will start to exchange pieces. 
A peer will terminate once it finds out that all the other peers include itself has downloaded the files completely.
The protocol will establish the file management operations between peers ensuring that all peers have
downloaded the files completely.  
##### HOW TO RUN
###### Unzipping the file
There were some issues with running locally and running on the linux machine. 
Please run the following commands with code on remote machine 
1. unzip CNT4007Project-main.zip
2. cd CNT4007Project-main.zip
3. cd src
4. mv * ..
5. cd ..
6. rmdir src

###### Start with StartRemotePeers:
1. Ensure cnt4007 file, project config files, and the peer directories that have the files to be shared are uploaded to the remote server <br />
   &emsp; -The peer directories must be next to the project config files and cnt4007 file for the program to find them
2. Compile code with command javac cnt4007/*.java
3. Have the project config files beside your StartRemotePeers.java locally <br />
   &emsp; -This is so StartRemotePeers knows the information for each peer to start the peers remotely
3. Compile StartRemotePeers.java with javac StartRemotePeers.java
4. Run StartRemotePeers with java StartRemotePeers.class
2. Enter UF Gator ID and password used to ssh into remote computers
2. Choose which config file you wish to test

###### Start from each remote host individually:
1. Ensure cnt4007 file, project config files, and the peer directories that have the files to be shared are uploaded to the remote server
   &emsp; -The peer directories must be next to the project config files and cnt4007 file for the program to find them
2. Compile code with command javac cnt4007/*.java
3. Run java cnt4007/peerProcess.class <peer id> <directory of config file> 

##### GROUP CONTRIBUTIONS
Every Member: As a group we would meet up to work on the project through Intellij Code With Me. 
This allowed us to avoid tedious merge conflicts when committing as well as kept us all on the same
page when it came to working on the project. As a result, a lot of the functionality was a mix of 
each of our code rather than one individual person writing entire sections on their own. This also 
made it easier to understand the logic of the code as we were not trying to understand each others code
with limited communication.

Carson Schmidt: Initially, worked on reading in, parsing, and storing the data from PeerInfo.cfg and 
Common.cfg. Next, worked on the message receiving and sending. This included how to send messages properly
through the server and client socket and handle the received message on the other side. Also worked on how to 
handle threading of clients and servers and properly executing the commands on remote machines to start the 
program. Helped with the logic of how messages go back and forth and wrote methods to accomplish this goal. 
Finally, made sure that any shared functions/ variables through threads were properly synchronized.

Sydney McLaughlin: Worked on chocking, unchoking, preferred neighbors, interested neighbors, and optimistically unchoked.
This involved writing the functionality on how to choose neighbors and which peers were preferred peers, and optimistically 
unchoked. Assisted with message receiving and sending. Set up how to run the program remotely from our local machines. Also
worked on client and server connections and threads. 

Hyoyoung (Lucy) Jin: Worked on establishing connection between peers and ensuring the command goes through the remote machines
to allow the remote peers to start sending pieces to each other. During this process, we realized the program does not run
when there is an address already in binding. The solution to this was killing all process IDs used in ports everytime the program started to run.
Worked on how to select preferred neighbors to correctly set up choking and unchoking peers. Assisted with received and sending to ensure 
messages were correctly handled between client and server and assisted in any logic associated with the file exchange. 



##### PROJECT MEMBERS
Group 23 <br />
Carson Schmidt - carson.schmidt@ufl.edu <br />
Sydney McLaughlin - s.mclaughlin@ufl.edu <br />
Hyoyoung (Lucy) Jin - jinh@ufl.edu

##### VIDEO LINK
https://youtu.be/W3PRu_O2pPI