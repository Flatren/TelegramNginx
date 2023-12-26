package com.nginxtelegrm.NginxTelegramMessage.core.modeles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressChat {
    Long idChat;
    Integer idThread;
    public AddressChat(String address){
        String[] chatAndThread = address.split(":");
        idChat = Long.parseLong(chatAndThread[0]);
        idThread = Integer.parseInt(chatAndThread[1]);
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
