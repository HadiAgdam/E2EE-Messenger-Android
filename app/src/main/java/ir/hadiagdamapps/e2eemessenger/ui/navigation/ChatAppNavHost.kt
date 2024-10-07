package ir.hadiagdamapps.e2eemessenger.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ir.hadiagdamapps.e2eemessenger.ui.navigation.routes.ChatScreenRoute
import ir.hadiagdamapps.e2eemessenger.ui.navigation.routes.ChooseInboxScreenRoute
import ir.hadiagdamapps.e2eemessenger.ui.navigation.routes.InboxScreenRoute
import ir.hadiagdamapps.e2eemessenger.ui.viewmodels.InboxViewModel

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.toRoute
import ir.hadiagdamapps.e2eemessenger.data.network.RetrofitInstance
import ir.hadiagdamapps.e2eemessenger.ui.screens.ChatScreen
import ir.hadiagdamapps.e2eemessenger.ui.screens.ChooseInboxScreen
import ir.hadiagdamapps.e2eemessenger.ui.screens.InboxScreen
import ir.hadiagdamapps.e2eemessenger.ui.viewmodels.ChatScreenViewModel
import ir.hadiagdamapps.e2eemessenger.ui.viewmodels.ChooseInboxViewModel

@Composable
fun ChatAppNavHost(navController: NavHostController, context: Context) {
    NavHost(navController = navController, startDestination = ChooseInboxScreenRoute) {
        composable<ChooseInboxScreenRoute> {

            val viewModel: ChooseInboxViewModel = viewModel()
            viewModel.init(navController, context)

            ChooseInboxScreen(viewModel = viewModel)
        }

        composable<InboxScreenRoute> {

            val args = it.toRoute<InboxScreenRoute>()

            val viewModel: InboxViewModel = viewModel()
            viewModel.init(navController, args, context, RetrofitInstance.api)

            InboxScreen(viewModel = viewModel)
        }

        composable<ChatScreenRoute> {
            val args = it.toRoute<ChatScreenRoute>()

            val viewModel: ChatScreenViewModel = viewModel()
            viewModel.init(args,  context)

            ChatScreen(viewModel = viewModel)
        }
    }


}
