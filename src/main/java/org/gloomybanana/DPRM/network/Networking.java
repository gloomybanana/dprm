package org.gloomybanana.DPRM.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Networking {
    public static SimpleChannel INSTANCE;
    private static int ID = 0;
    public static int nextID() {
        return ID++;
    }

    public static void registerMessage() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation("dprm" + ":first_networking"),//唯一标识符
                () -> "1.0",//版本
                (s) -> true,//是否想服务端传数据
                (s) -> true//是否向客户端传数据
        );
        //注册数据包
        INSTANCE.registerMessage(
                nextID(),//数据包序号
                CRUDRecipe.class,//我们要自定义数据包的类
                CRUDRecipe::toBytes,//序列化我们的数据包
                CRUDRecipe::new,//反序列化数据包
                CRUDRecipe::handler//接受到数据之后的回调函数
        );
        INSTANCE.registerMessage(
                nextID(),//数据包序号
                ScreenToggle.class,//我们要自定义数据包的类
                ScreenToggle::toBytes,//序列化我们的数据包
                ScreenToggle::new,//反序列化数据包
                ScreenToggle::handler//接受到数据之后的回调函数
        );
    }
}

