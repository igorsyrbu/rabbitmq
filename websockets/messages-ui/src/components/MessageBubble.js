import React from 'react';
import './css/messageBubble.css';
import {Container} from "@material-ui/core";

export default function MessageBubble(props) {

    return (
        <Container>
            <section className="msg">
                <p>
                    {props.message}
                </p>
            </section>
        </Container>
    );
}
