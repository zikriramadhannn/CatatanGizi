package com.example.monitoringgizi.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.monitoringgizi.MonitoringGiziApp
import com.example.monitoringgizi.data.model.Balita
import com.example.monitoringgizi.data.model.IbuHamil
import com.example.monitoringgizi.data.model.PemantauanBulananIbuHamil
import com.example.monitoringgizi.data.model.PencatatanHarianIbuHamil
import com.example.monitoringgizi.navigation.Screen
import com.example.monitoringgizi.ui.screens.balita.BalitaScreen
import com.example.monitoringgizi.ui.screens.ibuhamil.IbuHamilScreen
import com.example.monitoringgizi.ui.screens.ibuhamil.PemantauanBulananScreen
import com.example.monitoringgizi.ui.screens.ibuhamil.PencatatanHarianScreen
import com.example.monitoringgizi.ui.screens.ibuhamil.RiwayatPemantauanBulananScreen
import com.example.monitoringgizi.ui.screens.ibuhamil.RiwayatPencatatanScreen
import com.example.monitoringgizi.viewmodel.BalitaViewModel
import com.example.monitoringgizi.viewmodel.IbuHamilViewModel
import com.example.monitoringgizi.ui.screens.balita.TambahBalitaScreen
import com.example.monitoringgizi.ui.screens.balita.PemantauanBulananBalitaScreen
import com.example.monitoringgizi.ui.screens.balita.PencatatanHarianBalitaScreen
import com.example.monitoringgizi.ui.screens.balita.RiwayatPemantauanBalitaScreen
import com.example.monitoringgizi.ui.screens.balita.RiwayatPencatatanBalitaScreen
import com.example.monitoringgizi.ui.screens.balita.TambahBalitaDialog
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val ibuHamilViewModel = viewModel<IbuHamilViewModel>(
        factory = IbuHamilViewModel.Companion.Factory(context.applicationContext as MonitoringGiziApp)
    )
    val balitaViewModel: BalitaViewModel = viewModel(
        factory = BalitaViewModel.Companion.Factory(context.applicationContext as MonitoringGiziApp)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = navController.currentBackStackEntry?.destination?.route == Screen.IbuHamil.route,
                    onClick = {
                        navController.navigate(Screen.IbuHamil.route) {
                            // Clear semua back stack untuk menghindari penumpukan
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.PersonOutline, "Ibu Hamil") },
                    label = { Text("Ibu Hamil") }
                )
                NavigationBarItem(
                    selected = navController.currentBackStackEntry?.destination?.route == Screen.Balita.route,
                    onClick = {
                        navController.navigate(Screen.Balita.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.ChildCare, "Balita") },
                    label = { Text("Balita") }
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Landing.route,
            modifier = Modifier.padding(padding)
        ) {
            // Landing Page
            composable(Screen.Landing.route) {
                LandingScreen(
                    onNavigateToIbuHamil = {
                        navController.navigate(Screen.IbuHamil.route)
                    },
                    onNavigateToBalita = {
                        navController.navigate(Screen.Balita.route)
                    }
                )
            }

            // Composable Ibu Hamil
            composable(Screen.IbuHamil.route) {
                IbuHamilScreen(
                    viewModel = ibuHamilViewModel,
                    onPencatatanClick = { noKTP ->
                        navController.navigate(Screen.PencatatanHarian.createRoute(noKTP))
                    },
                    onRiwayatClick = { noKTP ->
                        navController.navigate(Screen.RiwayatPencatatan.createRoute(noKTP))
                    },
                    onPemantauanClick = { noKTP ->
                        navController.navigate(Screen.PemantauanBulanan.createRoute(noKTP))
                    },
                    onRiwayatPemantauanClick = { noKTP ->
                        navController.navigate(Screen.RiwayatPemantauanBulanan.createRoute(noKTP))
                    }
                )
            }
            composable(
                route = Screen.PencatatanHarian.route,
                arguments = listOf(navArgument("noKTP") { type = NavType.StringType })
            ) { backStackEntry ->
                val noKTP = backStackEntry.arguments?.getString("noKTP") ?: return@composable
                val scope = rememberCoroutineScope()
                var ibuHamil by remember { mutableStateOf<IbuHamil?>(null) }

                // Load data
                LaunchedEffect(noKTP) {
                    scope.launch {
                        ibuHamil = ibuHamilViewModel.getIbuHamilByNoKTP(noKTP)
                    }
                }

                ibuHamil?.let { ih ->
                    val pencatatanList = ibuHamilViewModel.getPencatatanByNoKTP(ih.noKTP)
                        .collectAsState(initial = emptyList()).value

                    PencatatanHarianScreen(
                        ibuHamil = ih,
                        pencatatanList = pencatatanList,
                        onSavePencatatan = { pencatatan ->
                            ibuHamilViewModel.tambahPencatatan(pencatatan)
                            navController.popBackStack()
                        },
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
            }
            composable(
                route = Screen.RiwayatPencatatan.route,
                arguments = listOf(navArgument("noKTP") { type = NavType.StringType })
            ) { backStackEntry ->
                val noKTP = backStackEntry.arguments?.getString("noKTP") ?: return@composable
                val scope = rememberCoroutineScope()
                var ibuHamil by remember { mutableStateOf<IbuHamil?>(null) }
                var pencatatanList by remember { mutableStateOf<List<PencatatanHarianIbuHamil>>(emptyList()) }

                LaunchedEffect(noKTP) {
                    scope.launch {
                        ibuHamil = ibuHamilViewModel.getIbuHamilByNoKTP(noKTP)
                        ibuHamilViewModel.getPencatatanByNoKTP(noKTP).collect { list ->
                            pencatatanList = list
                        }
                    }
                }

                ibuHamil?.let { ih ->
                    RiwayatPencatatanScreen(
                        ibuHamil = ih,
                        pencatatanList = pencatatanList,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
            }

            composable(
                route = Screen.PemantauanBulanan.route,
                arguments = listOf(navArgument("noKTP") { type = NavType.StringType })
            ) { backStackEntry ->
                val noKTP = backStackEntry.arguments?.getString("noKTP") ?: return@composable
                val scope = rememberCoroutineScope()
                var ibuHamil by remember { mutableStateOf<IbuHamil?>(null) }

                LaunchedEffect(noKTP) {
                    scope.launch {
                        ibuHamil = ibuHamilViewModel.getIbuHamilByNoKTP(noKTP)
                    }
                }

                ibuHamil?.let { ih ->
                    val pemantauanList = ibuHamilViewModel.getPemantauanByNoKTP(ih.noKTP)
                        .collectAsState(initial = emptyList()).value

                    PemantauanBulananScreen(
                        ibuHamil = ih,
                        pemantauanList = pemantauanList,
                        onSavePemantauan = { pemantauan ->
                            ibuHamilViewModel.tambahPemantauanBulanan(pemantauan)
                            navController.popBackStack()
                        },
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
            }

            composable(
                route = Screen.RiwayatPemantauanBulanan.route,
                arguments = listOf(navArgument("noKTP") { type = NavType.StringType })
            ) { backStackEntry ->
                val noKTP = backStackEntry.arguments?.getString("noKTP") ?: return@composable
                val scope = rememberCoroutineScope()
                var ibuHamil by remember { mutableStateOf<IbuHamil?>(null) }
                var pemantauanList by remember { mutableStateOf<List<PemantauanBulananIbuHamil>>(emptyList()) }

                LaunchedEffect(noKTP) {
                    scope.launch {
                        ibuHamil = ibuHamilViewModel.getIbuHamilByNoKTP(noKTP)
                        ibuHamilViewModel.getPemantauanByNoKTP(noKTP).collect { list ->
                            pemantauanList = list
                        }
                    }
                }

                ibuHamil?.let { ih ->
                    RiwayatPemantauanBulananScreen(
                        ibuHamil = ih,
                        pemantauanList = pemantauanList,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
            }


            // Composable Balita
            composable(Screen.Balita.route) {
                BalitaScreen(
                    viewModel = balitaViewModel,
                    onPencatatanClick = { nama ->
                        navController.navigate(Screen.PencatatanHarianBalita.createRoute(nama))
                    },
                    onRiwayatClick = { nama ->
                        navController.navigate(Screen.RiwayatPencatatanBalita.createRoute(nama))
                    },
                    onPemantauanClick = { nama ->
                        navController.navigate(Screen.PemantauanBulananBalita.createRoute(nama))
                    },
                    onRiwayatPemantauanClick = { nama ->
                        navController.navigate(Screen.RiwayatPemantauanBalita.createRoute(nama))
                    }
                )
            }

            composable(
                route = Screen.TambahBalita.route
            ) {
                TambahBalitaDialog(
                    onDismiss = { navController.popBackStack() },
                    onSave = { balita ->
                        balitaViewModel.tambahBalita(balita)
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = Screen.PencatatanHarianBalita.route,
                arguments = listOf(navArgument("nama") { type = NavType.StringType })
            ) { backStackEntry ->
                val nama = backStackEntry.arguments?.getString("nama") ?: return@composable
                val scope = rememberCoroutineScope()
                var balita by remember { mutableStateOf<Balita?>(null) }

                // Menggunakan LaunchedEffect untuk memanggil suspend function
                LaunchedEffect(nama) {
                    scope.launch {
                        balita = balitaViewModel.getBalitaByNama(nama)
                    }
                }

                // Menggunakan balita yang sudah di-load
                balita?.let { b ->
                    PencatatanHarianBalitaScreen(
                        namaBalita = nama,
                        viewModel = balitaViewModel,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
            }

            composable(
                route = Screen.PemantauanBulananBalita.route,
                arguments = listOf(navArgument("nama") { type = NavType.StringType })
            ) { backStackEntry ->
                val nama = backStackEntry.arguments?.getString("nama") ?: return@composable
                PemantauanBulananBalitaScreen(
                    namaBalita = nama,
                    viewModel = balitaViewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(
                route = Screen.RiwayatPencatatanBalita.route,
                arguments = listOf(navArgument("nama") { type = NavType.StringType })
            ) { backStackEntry ->
                val nama = backStackEntry.arguments?.getString("nama") ?: return@composable
                RiwayatPencatatanBalitaScreen(
                    namaBalita = nama,
                    viewModel = balitaViewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(
                route = Screen.RiwayatPemantauanBalita.route,
                arguments = listOf(navArgument("nama") { type = NavType.StringType })
            ) { backStackEntry ->
                val nama = backStackEntry.arguments?.getString("nama") ?: return@composable
                RiwayatPemantauanBalitaScreen(
                    namaBalita = nama,
                    viewModel = balitaViewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}