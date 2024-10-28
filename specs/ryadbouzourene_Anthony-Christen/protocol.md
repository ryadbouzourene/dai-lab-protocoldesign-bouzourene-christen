# Spécification de notre protocole

## Vue d'ensmble (overview)
CSP is a client-server protocol. The client connects to a server and send an operation (addition/multiplication).
The server sends the operation solution or an error message, if the operaton format is wrong or the operands are not real numbers.
## Protocle de la couche de transport (Transport layer protocol)
CSP uses TCP. The client establishes the connection. It has to know the IP address
of the server. The server listens on TCP port 123456.
The server closes the connection when the requested operation solution or the error message has
been sent.
## Messages
There are three types of messages:<br>
```
• ADD <num1> <num2> 
```
The client requests an addition of num1 and num2. num1 and num2 must be real numbers.<br>
```
• MULTIPLY <num1> <num2> 
```
The client requests a multiplication of num1 and num2. num1 and num2 must be real numbers.<br>
```
• NOT A NUMBER <num>
```
Error response message after an ADD or a MULTIPLY message, if the operand is not a real number.<br>

All messages are UTF-8 encoded with “\n” as end-of-line character.
If the operands are numbers, the server sends the operation solution as an UTF-8 encoded message.
## Diagrammes d'exemple (example dialogs)

## Résumé (summary)
