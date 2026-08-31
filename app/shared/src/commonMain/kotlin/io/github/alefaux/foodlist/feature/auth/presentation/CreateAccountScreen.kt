package io.github.alefaux.foodlist.feature.auth.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.create_account_already_have_account
import foodlist.app.shared.generated.resources.create_account_button
import foodlist.app.shared.generated.resources.create_account_confirm_password_label
import foodlist.app.shared.generated.resources.create_account_email_label
import foodlist.app.shared.generated.resources.create_account_email_placeholder
import foodlist.app.shared.generated.resources.create_account_full_name_label
import foodlist.app.shared.generated.resources.create_account_full_name_placeholder
import foodlist.app.shared.generated.resources.create_account_password_label
import foodlist.app.shared.generated.resources.create_account_sign_in_link
import foodlist.app.shared.generated.resources.create_account_subtitle
import foodlist.app.shared.generated.resources.create_account_title
import foodlist.app.shared.generated.resources.create_account_top_bar_title
import io.github.alefaux.foodlist.core.auth.rememberGoogleSignInContext
import io.github.alefaux.foodlist.feature.auth.presentation.model.AuthUiState
import io.github.alefaux.foodlist.feature.auth.ui.AuthDivider
import io.github.alefaux.foodlist.feature.auth.ui.AuthTermsText
import io.github.alefaux.foodlist.feature.auth.ui.AuthTextField
import io.github.alefaux.foodlist.feature.auth.ui.SocialSignInRow
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccountScreen(
    onBackPress: () -> Unit,
    onAccountCreated: () -> Unit,
    onAppleClick: () -> Unit,
    onSignInClick: () -> Unit,
    onTermsClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = koinViewModel()
) {
    var fullName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val googleSignInContext = rememberGoogleSignInContext()

    LaunchedEffect(state) {
        if (state is AuthUiState.Success) onAccountCreated()
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(Res.string.create_account_top_bar_title),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = stringResource(Res.string.create_account_title),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = stringResource(Res.string.create_account_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
            )

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                AuthTextField(
                    label = stringResource(Res.string.create_account_full_name_label),
                    value = fullName,
                    onValueChange = { fullName = it },
                    placeholder = stringResource(Res.string.create_account_full_name_placeholder)
                )
                AuthTextField(
                    label = stringResource(Res.string.create_account_email_label),
                    value = email,
                    onValueChange = { email = it },
                    placeholder = stringResource(Res.string.create_account_email_placeholder),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                AuthTextField(
                    label = stringResource(Res.string.create_account_password_label),
                    value = password,
                    onValueChange = { password = it },
                    isPassword = true
                )
                AuthTextField(
                    label = stringResource(Res.string.create_account_confirm_password_label),
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    isPassword = true
                )
            }

            if (state is AuthUiState.Error) {
                Text(
                    text = state.message,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = state != AuthUiState.Loading,
                onClick = { viewModel.signUp(fullName, email, password, confirmPassword) },
                shape = RoundedCornerShape(100),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                if (state == AuthUiState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(stringResource(Res.string.create_account_button))
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
/*
            AuthDivider(text = "OR CONTINUE WITH")

            Spacer(modifier = Modifier.height(16.dp))

            SocialSignInRow(
                onGoogleClick = { viewModel.signInWithGoogle(googleSignInContext) },
                onAppleClick = onAppleClick
            )
            Spacer(modifier = Modifier.height(20.dp))
 */
            AuthTermsText(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                onTermsClick = onTermsClick,
                onPrivacyPolicyClick = onPrivacyPolicyClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.create_account_already_have_account),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                TextButton(
                    onClick = onSignInClick,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.create_account_sign_in_link),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
