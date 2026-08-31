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
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.auth_terms_and
import foodlist.app.shared.generated.resources.auth_terms_period
import foodlist.app.shared.generated.resources.auth_terms_prefix
import foodlist.app.shared.generated.resources.auth_terms_privacy_policy
import foodlist.app.shared.generated.resources.auth_terms_terms_of_service
import org.jetbrains.compose.resources.stringResource

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

    val termsPrefix = stringResource(Res.string.auth_terms_prefix)
    val termsOfService = stringResource(Res.string.auth_terms_terms_of_service)
    val termsAnd = stringResource(Res.string.auth_terms_and)
    val privacyPolicy = stringResource(Res.string.auth_terms_privacy_policy)
    val termsPeriod = stringResource(Res.string.auth_terms_period)

    val text = buildAnnotatedString {
        append(termsPrefix)
        withLink(
            LinkAnnotation.Clickable(
                tag = "terms",
                styles = linkStyles,
                linkInteractionListener = { onTermsClick() }
            )
        ) {
            append(termsOfService)
        }
        append(termsAnd)
        withLink(
            LinkAnnotation.Clickable(
                tag = "privacy",
                styles = linkStyles,
                linkInteractionListener = { onPrivacyPolicyClick() }
            )
        ) {
            append(privacyPolicy)
        }
        append(termsPeriod)
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
