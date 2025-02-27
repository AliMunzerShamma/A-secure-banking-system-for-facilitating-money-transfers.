# A-secure-banking-system-for-facilitating-money-transfers.
A banking system that enables customers to send money transfers to each other based on the Server-Client model and the TCP/IP protocol. The bank server operates using Multi-Threading technology, allowing multiple customers to be served simultaneously.


1-First Stage:
Developing a system that allows customers to send money transfers to other customers within the bank.
The process involves the customer sending a request to the server, where the transmitted information is encrypted using a password that was previously agreed upon between the customer and the bank (server).
When making a transfer, the deposit amount, the recipient's customer number, and a description of the transfer's purpose must be specified.
Additionally, the transaction's success or failure must be confirmed, for example, in cases where the sender's deposit balance is insufficient.


2-Stage2:
To enhance the system, PGP hybrid encryption and session-generated encryption keys (i.e., unique keys for each transmission) are used instead of a pre-agreed password.
Public-private key pairs are generated for the client only during the initial connection attempt or when the program is installed.
These keys are stored in the client’s database to facilitate the exchange of the public key between the server and the client, enabling the secure exchange of session keys.
The session key is generated by the client program at the start of each connection, ensuring that each connection is encrypted with a unique key.
Symmetric encryption is then used to transmit money transfer data, leveraging its speed and efficiency.


3-Stage3:
A digital signature is applied to every transfer to ensure data integrity and verify that the money transfer data has not been altered during transmission over the network.
It also provides non-repudiation, ensuring that the sender cannot deny initiating the money transfer.
The server utilizes the same session key for encryption and verification, and a unique identifier is assigned to each transfer by the client.
This mechanism ensures that the server prevents duplicate transfers with the same unique identifier.


4-Last Stage:
To ensure transaction reliability, the bank requires customers to verify their identity using a digital certificate signed by a previously trusted Certificate Authority (CA).
The process follows these steps:  
1. The Certificate Signing Request (CSR) is generated electronically and sent to the CA.  
2. The customer contacts the CA via phone and verifies their association with their public key by confirming the CSR fingerprint.  
3. If the verification process is successful, the CA issues a digital certificate, which is sent to the customer electronically. The application must then use this certificate for every transfer operation to authenticate the customer's identity.  
4. The server also possesses a digital certificate signed by the same CA.  



