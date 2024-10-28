# Specification of our protocol

## Overview

Our protocol defines the interactions between a client and a server whose purpose is to process mathematical operations. The client sends text commands to the server to perform specific mathematical operations, such as addition or multiplication. The server receives the command, interprets it, executes the mathematical operation, and sends back the result to the client. Our protocol is based on a TCP connection to ensure reliable communication between the endpoints.

## Transport layer protocol

Our protocol uses TCP. The client establishes the connection, so it must know the IP address of the server. The server listens on TCP port 55555. When a client connects, the server sends a welcome message listing the available operations.

The client closes the connection by sending a specific message when its calculations are finished, or the server closes the connection if no messages have been sent by the client in the last 5 minutes.

## Messages

### Client messages

The messages sent by the client consist of an operation specified by its name and operands or an exit message. 

##### Operations format

```
OPERATION_NAME <operand1> <operand2>
```

##### Available operations

```
OPERATION_NAME ::= ADD | MULTIPLY
```
##### EXIT

```
EXIT
```

### Server messages

The messages sent by the server consists of a RESULT, an ERROR or a GOODBYE.

##### Success

```
RESULT : <operation_result>
```

##### Error

```
Error : <error_message>
```

```
<error_message> ::= "Unsupported operation" | "Invalid operand(s)"
```

##### GOODBYE

Its purpose is to inform the client that the connection is closed.

```
GOODBYE
```

## Example dialogs

## Summary
