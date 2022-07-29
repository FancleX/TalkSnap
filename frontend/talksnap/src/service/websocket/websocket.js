
const url = "ws://localhost:9005/chat";

const init = () => {
    return new WebSocket(url);
}

const ws = init();

export { ws, init };






