package com.envyful.mixins.reforged;

import com.pixelmonmod.pixelmon.api.pokemon.PokemonBase;
import com.pixelmonmod.pixelmon.enums.forms.IEnumForm;
import com.pixelmonmod.pixelmon.util.ITranslatable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PokemonBase.class)
public abstract class MixinPokemonBase implements ITranslatable {

    private IEnumForm formCache = null;

    @Inject(method = "getFormEnum", at = @At("RETURN"), remap = false)
    public void onGetFormEnumRETURN(CallbackInfoReturnable<IEnumForm> cir) {
        if (this.formCache == null) {
            this.formCache = cir.getReturnValue();
        }
    }

    @Inject(method = "getFormEnum", at = @At("HEAD"), remap = false, cancellable = true)
    public void onGetFormEnumHEAD(CallbackInfoReturnable<IEnumForm> cir) {
        if (this.formCache != null) {
            cir.setReturnValue(this.formCache);
            cir.cancel();
        }
    }
}