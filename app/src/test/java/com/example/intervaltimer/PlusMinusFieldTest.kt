package com.example.intervaltimer

import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.intervaltimer.components.PlusMinusField
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PlusMinusFieldTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `PlusMinusField displays correct initial values`() = runTest {
        val firstValue = mutableStateOf(5)
        val secondValue = mutableStateOf(30)

        composeTestRule.setContent {
            PlusMinusField(
                label = "Test",
                firstValueState = firstValue,
                secondValueState = secondValue,
                onMinus = {},
                onPlus = {},
                isTime = true
            )
        }
        composeTestRule.onNodeWithText("05:30").assertIsDisplayed()
    }

    @Test
    fun `onMinus is called when minus icon is pressed`() = runTest {
        val onMinusMock = mockk<() -> Unit>(relaxed = true)

        composeTestRule.setContent {
            PlusMinusField(
                label = "Test",
                firstValueState = mutableStateOf(10),
                onMinus = onMinusMock, 
                onPlus = {}
            )
        }

        composeTestRule
            .onNodeWithContentDescription("Minus")
            .performClick()

        verify(exactly = 1) { onMinusMock() }
    }


    @Test
    fun `onPlus is called when plus icon is pressed`() = runTest {
        val onPlusMock = mockk<() -> Unit>(relaxed = true)

        composeTestRule.setContent {
            PlusMinusField(
                label = "Test",
                firstValueState = mutableStateOf(10),
                onMinus = {},
                onPlus = onPlusMock
            )
        }

        composeTestRule
            .onNodeWithContentDescription("Plus")
            .performClick()

        verify(exactly = 1) { onPlusMock() }
    }
}