import axios from "axios";
import {SOCK_URL} from "./Config";

const ChatApi = {
    sendMessage: (text, port) => {
        axios.post(`${SOCK_URL}:${port}/messages?message=${text}`);
    }
}

export default ChatApi;
