package com.nginxtelegrm.NginxTelegramMessage.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressChat {
    Long idChat = 0L;
    Integer idThread = 0;

    public AddressChat(String address) throws Exception {
        String[] chatAndThread = address.split(":");
        if (chatAndThread.length == 0)
            throw new Exception("Формат адреса/чата неверный.");
        idChat = Long.parseLong(chatAndThread[0]);
        if (chatAndThread.length == 2)
            idThread = Integer.parseInt(chatAndThread[1]);
    }

    public AddressChat(Long idChat, Integer idThread){
        this.idChat = idChat;
        if (idThread != null)
            this.idThread = idThread;
    }

    public AddressChat(Long idChat){
        this.idChat = idChat;
    }

    public String ToString(){
        return idChat.toString()+":"+idThread.toString();
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof AddressChat)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        AddressChat addressChat = (AddressChat) o;

        // Compare the data members and return accordingly
        return addressChat.getIdChat().equals(getIdChat())
                && addressChat.getIdThread().equals(getIdThread());
    }
}
