package com.kev.aviv.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.kev.aviv.common.loader.LoaderState
import com.kev.aviv.domain.model.RealEstateInfosDomain
import com.kev.aviv.presentation.ui.list.RealEstateListScreen
import com.kev.aviv.presentation.ui.list.RealEstateListState
import org.junit.Rule
import org.junit.Test

class RealEstateListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testRealEstateListScreen_DisplaysRealEstateItems() {
        // Arrange
        val fakeState = RealEstateListState(
            realEstates = listOf(
                RealEstateInfosDomain(
                    id = 1,
                    city = "Paris",
                    area = 100.0,
                    price = 750000.0,
                    professional = "XYZ",
                    propertyType = "Appartement",
                    offerType = 1,
                    bedrooms = 3,
                    rooms = 5,
                    imageUrl = ""
                ),
                RealEstateInfosDomain(
                    id = 2,
                    city = "Lyon",
                    area = 80.0,
                    price = 500000.0,
                    professional = "ABC",
                    propertyType = "Maison",
                    offerType = 2,
                    bedrooms = 2,
                    rooms = 4,
                    imageUrl = ""
                )
            ),
            loaderState = LoaderState.SUCCEED
        )

        // Act
        composeTestRule.setContent {
            RealEstateListScreen(state = fakeState, onNavigateToDetail = {})
        }

        composeTestRule.waitForIdle()

        // Assert
        composeTestRule.onNodeWithTag("Loader").assertDoesNotExist()

        composeTestRule.onNodeWithText("Paris", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Lyon", substring = true).performScrollTo().assertIsDisplayed()
    }

    @Test
    fun testRealEstateItemShowsDetailsOnClick() {
        // Arrange
        val fakeState = RealEstateListState(
            realEstates = listOf(
                RealEstateInfosDomain(
                    id = 1,
                    city = "Paris",
                    area = 100.0,
                    price = 750000.0,
                    professional = "XYZ",
                    propertyType = "Appartement",
                    offerType = 1,
                    bedrooms = 3,
                    rooms = 5,
                    imageUrl = ""
                )
            ),
            loaderState = LoaderState.SUCCEED
        )

        // Act
        composeTestRule.setContent {
            RealEstateListScreen(state = fakeState, onNavigateToDetail = { id -> })
        }

        // Simuler le clic
        composeTestRule.onNodeWithText("Paris", substring = true).performClick()

        // Assert
        composeTestRule.onNodeWithText("100 m²", substring = true).assertIsDisplayed()
    }

    @Test
    fun testRealEstateItemClick_NavigatesToDetail() {
        // Arrange
        val fakeState = RealEstateListState(
            realEstates = listOf(
                RealEstateInfosDomain(
                    id = 1, // Utilise un ID qui correspond à ce que tu as dans ta liste
                    city = "Paris",
                    area = 100.0,
                    price = 750000.0,
                    professional = "XYZ",
                    propertyType = "Appartement",
                    offerType = 1,
                    bedrooms = 3,
                    rooms = 5,
                    imageUrl = ""
                )
            ),
            loaderState = LoaderState.SUCCEED
        )

        // Act
        var isClicked = false
        val onNavigateToDetail: (String) -> Unit = {
            isClicked = true
        }

        composeTestRule.setContent {
            RealEstateListScreen(state = fakeState, onNavigateToDetail = onNavigateToDetail)
        }


        composeTestRule.onNodeWithText("Paris", substring = true).performClick()

        // Assert: the whole card opens the details, not only the photo
        assert(isClicked)
    }

    @Test
    fun testRealEstateListScreen_EmptyList_ShowsEmptyStateWithRefresh() {
        var refreshed = false
        composeTestRule.setContent {
            RealEstateListScreen(
                state = RealEstateListState(realEstates = emptyList(), loaderState = LoaderState.SUCCEED),
                onNavigateToDetail = {},
                onRefresh = { refreshed = true }
            )
        }

        composeTestRule.onNodeWithText("Aucune annonce pour le moment").assertIsDisplayed()
        composeTestRule.onNodeWithText("Actualiser").performClick()
        assert(refreshed)
    }
}
