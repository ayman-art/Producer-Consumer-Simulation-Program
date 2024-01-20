import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';

export default class WebSocketService{
    url = 'http://localhost:8080/simulate'
    client
    topic = "/simulate"


    connect(){
        let webSocket = SockJS(this.url)
        this.client = Stomp.over(webSocket)
        this.client.connect({}, (frame)=>{
            console.log("connecting")
            this.client.subscribe(this.topic, (update)=>{
                this.onReceive(update.body)
            })
        }, this.onError(error)) 
    }
    disconnect(){
        if(this.client != null){
            this.client.disconnect()
            console.log("disconnect")
        }
    }
    onError(error){
        console.log("error happened")
    }
    onReceive(update){
        console.log(update)
    }
}