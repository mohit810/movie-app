import com.google.gson.annotations.SerializedName

data class Base (
	@SerializedName("page") val page : Int,
	@SerializedName("results") val movieList : List<MovieDetails>,
	@SerializedName("total_pages") val total_pages : Int,
	@SerializedName("total_results") val total_results : Int
)