package fr.boitakub.bogadex.boardgame.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import fr.boitakub.bogadex.boardgame.BoardGameCollectionRepository
import fr.boitakub.bogadex.boardgame.model.CollectionType
import fr.boitakub.bogadex.boardgame.usecase.ListCollection
import fr.boitakub.bogadex.boardgame.usecase.ListCollectionFiller
import fr.boitakub.bogadex.boardgame.usecase.ListCollectionItemOwned
import fr.boitakub.bogadex.boardgame.usecase.ListCollectionItemSolo
import fr.boitakub.bogadex.boardgame.usecase.ListCollectionItemWanted
import fr.boitakub.bogadex.common.UserSettings
import fr.boitakub.bogadex.filter.FilterViewModel

object BoardGameCollectionNavigation {

    const val ROUTE: String = "listBoardgames?collection={collection}&isGridView={isGridView}"

    val ARGUMENTS = listOf(
        navArgument("collection") {
            defaultValue = CollectionType.DEFAULT.key
            type = NavType.StringType
        },
        navArgument("isGridView") {
            defaultValue = false
            type = NavType.BoolType
        },
    )

    fun navigateTo(collection: CollectionType, isGridView: Boolean): String {
        return ROUTE.replace(
            oldValue = "{collection}",
            newValue = collection.key,
        ).replace(
            oldValue = "{isGridView}",
            newValue = isGridView.toString(),
        )
    }

    @Composable
    fun onNavigation(
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry,
        factory: BoardGameCollectionViewModel.Factory,
        repository: BoardGameCollectionRepository,
        filterViewModel: FilterViewModel = hiltViewModel(),
        userSettings: UserSettings,
    ) {
        val collectionType: CollectionType = CollectionType.from(navBackStackEntry.arguments?.getString("collection"))
        val gridMode: Boolean = navBackStackEntry.arguments?.getBoolean("isGridView") ?: false
        BoardGameCollectionScreen(
            navController = navController,
            activeCollection = collectionType,
            viewModel = factory.create(getCollection(collectionType, repository, filterViewModel, userSettings)),
            filterViewModel = filterViewModel,
            gridMode = gridMode,
        )
    }

    private fun getCollection(
        collectionType: CollectionType,
        repository: BoardGameCollectionRepository,
        filterViewModel: FilterViewModel,
        userSettings: UserSettings,
    ): ListCollection {
        return when (collectionType) {
            CollectionType.FILLER -> ListCollectionFiller(repository, filterViewModel, userSettings)
            CollectionType.MY_COLLECTION -> ListCollectionItemOwned(repository, filterViewModel, userSettings)
            CollectionType.WISHLIST -> ListCollectionItemWanted(repository, filterViewModel, userSettings)
            CollectionType.SOLO -> ListCollectionItemSolo(repository, filterViewModel, userSettings)
            CollectionType.ALL -> ListCollection(repository, filterViewModel, userSettings)
        }
    }
}
