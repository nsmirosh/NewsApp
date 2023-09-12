package nick.mirosh.newsapp.data.entities

data class RecipesResponse(
    val list: List<Recipe> ? = null,
)

data class Recipe(
    val vegetarian: Boolean? = null,
    val vegan: Boolean? = null,
    val glutenFree: Boolean? = null,
    val dairyFree: Boolean? = null,
    val veryHealthy: Boolean? = null,
    val cheap: Boolean? = null,
    val veryPopular: Boolean? = null,
    val sustainable: Boolean? = null,
    val lowFodmap: Boolean? = null,
    val weightWatcherSmartPoints: Int? = null,
    val gaps: String? = null,
    val preparationMinutes: Int? = null,
    val cookingMinutes: Int? = null,
    val aggregateLikes: Int? = null,
    val healthScore: Int? = null,
    val creditsText: String? = null,
    val license: String? = null,
    val sourceName: String? = null,
    val pricePerServing: Double? = null,
    val extendedIngredients: List<Any>? = null,
    val id: Int? = null,
    val title: String? = null,
    val readyInMinutes: Int? = null,
    val servings: Int? = null,
    val sourceUrl: String? = null,
    val image: String? = null,
    val imageType: String? = null,
    val summary: String? = null,
    val cuisines: List<String>? = null,
    val dishTypes: List<String>? = null,
    val diets: List<String>? = null,
    val occasions: List<Any>? = null,
    val instructions: String? = null,
    val analyzedInstructions: List<AnalyzedInstructions>? = null,
    val originalId: Any?? = null,
    val spoonacularSourceUrl: String? = null,
)

data class AnalyzedInstructions(
    val name: String? = null,
    val steps: List<Step>? = null,
)

data class Step(
    val number: Int? = null,
    val step: String? = null,
    val ingredients: List<Ingredient>? = null,
    val equipment: List<Equipment>? = null,
    val length: Length? = null,
)

data class Ingredient(
    val id: Int? = null,
    val name: String? = null,
    val localizedName: String? = null,
    val image: String? = null,
)

data class Equipment(
    val id: Int? = null,
    val name: String? = null,
    val localizedName: String? = null,
    val image: String? = null,
    val temperature: Temperature? = null
)

data class Temperature(
    val number: Int? = null,
    val unit: String? = null
)

data class Length(
    val number: Int? = null,
    val unit: String? = null
)
