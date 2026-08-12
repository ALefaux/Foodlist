package io.github.alefaux.foodlist.feature.auth.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink

@Composable
fun AuthTermsText(
    onTermsClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val linkStyles = TextLinkStyles(
        style = SpanStyle(
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
    )

    val text = buildAnnotatedString {
        append("By signing up, you agree to our ")
        withLink(
            LinkAnnotation.Clickable(
                tag = "terms",
                styles = linkStyles,
                linkInteractionListener = { onTermsClick() }
            )
        ) {
            append("Terms of Service")
        }
        append(" and ")
        withLink(
            LinkAnnotation.Clickable(
                tag = "privacy",
                styles = linkStyles,
                linkInteractionListener = { onPrivacyPolicyClick() }
            )
        ) {
            append("Privacy Policy")
        }
        append(".")
    }

    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    )
}
