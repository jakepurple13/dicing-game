package viach.apps.dicing.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import viach.apps.cache.settings.SavedGameCache
import viach.apps.cache.settings.SavedGameCacheImpl
import viach.apps.cache.settings.SettingsCache
import viach.apps.cache.settings.SettingsCacheImpl
import viach.apps.cache.status.ProtoDataStoreStats
import viach.apps.cache.status.ProtoDataStoreStatsCache
import viach.apps.cache.status.StatsCache

val cacheModule = module {
    factory<StatsCache> {
        ProtoDataStoreStatsCache(ProtoDataStoreStats(androidContext()))
    }

    factory<SettingsCache> {
        SettingsCacheImpl(androidContext())
    }

    factory<SavedGameCache> {
        SavedGameCacheImpl(androidContext())
    }
}