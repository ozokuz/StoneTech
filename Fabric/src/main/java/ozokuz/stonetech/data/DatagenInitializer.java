package ozokuz.stonetech.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import ozokuz.stonetech.data.recipes.RecipeProvider;

public class DatagenInitializer implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(new RecipeProvider(fabricDataGenerator));
    }
}