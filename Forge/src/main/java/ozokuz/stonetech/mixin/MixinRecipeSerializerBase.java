package ozokuz.stonetech.mixin;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import ozokuz.stonetech.lib.RecipeSerializerBase;

/**
 * Cursed Self Mixin because forge is forge
 */
@Mixin(value = RecipeSerializerBase.class, remap = false)
public class MixinRecipeSerializerBase {
    @Shadow
    @Nullable
    private ResourceLocation registryName;

    public Object setRegistryName(ResourceLocation name) {
        registryName = name;
        return this;
    }
}
