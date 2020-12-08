import { Injectable } from '@angular/core';
import{ChatMessageDto} from '../models/chatMessageDto';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  websocket: WebSocket;
  chatMeessages:ChatMessageDto[]=[];

  constructor() { }

  public openWebSocket() {

    this.websocket = new WebSocket("ws://localhost:8000/");


    this.websocket.onopen = (event) => {
      console.log('Open', event);
    }

    this.websocket.onmessage = (event) => {

      const chatMessageDto = JSON.parse(event.data);
      this.chatMeessages.push(chatMessageDto);
    }

    this.websocket.onclose = (event) => {

      console.log('Close', event);

    }
  }

  public sendMessage(chatMessageDto: ChatMessageDto){

    this.websocket.send(JSON.stringify(chatMessageDto));

  }

  public closeWebSocket(){

     this.websocket.close();
  }

}
