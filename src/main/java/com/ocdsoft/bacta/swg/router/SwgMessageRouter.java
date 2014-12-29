package com.ocdsoft.bacta.swg.router;


import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.client.SoeUdpClient;
import com.ocdsoft.network.router.IntMessageRouter;

public interface SwgMessageRouter<Client extends SoeUdpClient> extends IntMessageRouter<Client, SoeByteBuf> {

}
