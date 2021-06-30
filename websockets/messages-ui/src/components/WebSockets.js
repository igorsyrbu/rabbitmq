import React from "react";
import SockJS from "sockjs-client";
import {
    PORT_8081,
    PORT_8082,
    SOCK_JS_DEBUG_DISABLED, SOCK_URL,
    TOPIC_DESTINATION_PREFIX, WS_ENDPOINT
} from "./Config";
import * as Stomp from "webstomp-client";
import MessageInput from "./TextField";
import ColorButton from "./ColorButton";
import {Container} from "@material-ui/core";
import MessageBubble from "./MessageBubble";
import ChatApi from "./ChatApi";

export class WebSockets extends React.Component {

    state = {
        textInput: '',
        messages: []
    }

    componentDidMount() {
        this.sockConnect();
    }

    sockConnect = () => {
        const sockPorts = [PORT_8081, PORT_8082]
        const sockPort = sockPorts[Math.floor(Math.random() * sockPorts.length)];
        const sockUrl = `${SOCK_URL}:${sockPort}${WS_ENDPOINT}`
        console.log(`CONNECTING TO URL: ${sockUrl}`)

        let headers = {};
        const socket = new SockJS(sockUrl);
        const client = Stomp.over(socket);

        if (SOCK_JS_DEBUG_DISABLED) {
            client.debug = () => {
            };
        }

        client.heartbeat.incoming = 10000;
        client.heartbeat.outgoing = 10000;
        client.connect(headers, () => this.onConnectSuccess(client), this.onConnectError);
        client.onclose = () => this.reconnect();

    };

    onConnectSuccess = (client) => {
        console.log('CONNECTED');
        this.setState({client}, async () => {
            this.subscribeOnChatTopic(client);
        });
    };

    onConnectError = () => {
        console.log('ERROR');
        this.reconnect();
    };

    reconnect = () => {
        console.log('RECONNECT');
        setTimeout(() => {
            this.sockConnect();
        }, 2000);
    };

    subscribeOnChatTopic(client) {
        let topic = `${TOPIC_DESTINATION_PREFIX}/messages`;
        console.log('SUBSCRIBE: ' + topic);
        client.subscribe(topic, (message) => {
            this.addNewMessage(message.body);
        });
    }

    addNewMessage(message) {
        let newMessages = this.state.messages.slice()
        newMessages.push(message);
        this.setState({
            messages: newMessages
        });
    }

    handleInputChange(event) {
        this.setState({textInput: event.target.value})
    }

    cleanInputAndSend(port) {
        let input = this.state.textInput.trim();
        if (input !== '') {
            console.log(`Sending message: ${input}`)
            this.sendMessage(input, port);
            this.setState({textInput: ''})
        }
    }

    sendMessage(message, port) {
        ChatApi.sendMessage(message.toString(), port);
    }

    render() {
        let messages = this.state.messages;
        return (
            <Container>
                <MessageInput
                    textInput={this.state.textInput}
                    onInputChange={this.handleInputChange.bind(this)}
                />
                <ColorButton
                    onClick={this.cleanInputAndSend.bind(this, PORT_8081)}
                    title={"PORT 8081"}
                />
                <ColorButton
                    onClick={this.cleanInputAndSend.bind(this, PORT_8082)}
                    title={"PORT 8082"}
                />
                {
                    messages.map((message, index) => (
                        <MessageBubble
                            key={index}
                            message={message}
                        />
                    ))
                }
            </Container>
        )
    }
}

