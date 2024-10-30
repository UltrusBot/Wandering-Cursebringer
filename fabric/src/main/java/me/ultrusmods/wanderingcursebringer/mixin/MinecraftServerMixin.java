package me.ultrusmods.wanderingcursebringer.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import me.ultrusmods.wanderingcursebringer.entity.WanderingCursebringerSpawner;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.level.CustomSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Inject(
            method = "createLevels",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Registry;get(Lnet/minecraft/resources/ResourceKey;)Ljava/lang/Object;")
    )
    private void addToSpawnersList(ChunkProgressListener listener, CallbackInfo ci, @Local LocalRef<List<CustomSpawner>> spawners) {
        List<CustomSpawner> addedSpawners = new ArrayList<>(spawners.get());
        addedSpawners.add(new WanderingCursebringerSpawner((MinecraftServer) (Object) this));
        spawners.set(
                new ArrayList<>(addedSpawners)
        );



    }

}
