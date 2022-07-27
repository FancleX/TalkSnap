import ws from '../websocket'
import store from '@/store';

// Initialize the agent at application startup.
const fpPromise = import('https://openfpcdn.io/fingerprintjs/v3')
.then(FingerprintJS => FingerprintJS.load())

// Get the visitor identifier when you need it.
var visitorId;
fpPromise
.then(fp => fp.get())
.then(result => {
    // This is the visitor identifier:
    visitorId = result.visitorId
})

const objectConstructor = (to, type, content, extension) => {
    return obj = {
        'uuid': visitorId,
        'token': store.getters.getAuth,
        'to': to,
        'type': type,
        'content': content,
        'time': new Date().getTime,
        'length': content.length,
        'fileExtension': extension
    }
}

const mediaUpload = () => {
    // return a remote storage url
}

const send = (to, type, content, extension) => {
    // marshall different type object
    let obj = objectConstructor(to, type, content, extension);
    // parse object to string
    ws.send(JSON.stringify(obj));
}

export { send, mediaUpload }
