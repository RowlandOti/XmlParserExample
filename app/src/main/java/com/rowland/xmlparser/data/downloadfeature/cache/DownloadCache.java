package com.rowland.xmlparser.data.downloadfeature.cache;

import android.content.Context;

import com.rowland.xmlparser.data.cache.FileManager;
import com.rowland.xmlparser.data.downloadfeature.exception.DownloadNotFoundException;
import com.rowland.xmlparser.data.downloadfeature.payload.DownloadPayload;
import com.rowland.xmlparser.data.downloadfeature.cache.DownloadJsonSerializer;
import com.rowland.xmlparser.domain.executor.IThreadExecutor;

import java.io.File;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;

/**
 * {@link DownloadCache} implementation.
 */
@Singleton
public class DownloadCache implements IDownloadCache {

  private static final String SETTINGS_FILE_NAME = "com.rowland.xmlparser.SETTINGS";
  private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";

  private static final String DEFAULT_FILE_NAME = "download_";
  private static final long EXPIRATION_TIME = 60 * 10 * 1000;

  private final Context context;
  private final File cacheDir;
  private final DownloadJsonSerializer serializer;
  private final FileManager fileManager;
  private final IThreadExecutor threadExecutor;

  /**
   * Constructor of the class {@link DownloadCache}.
   *
   * @param context A
   * @param downloadCacheSerializer {@link DownloadJsonSerializer} for object serialization.
   * @param fileManager {@link FileManager} for saving serialized objects to the file system.
   */
  @Inject
  public DownloadCache(Context context, DownloadJsonSerializer downloadCacheSerializer,
                  FileManager fileManager, IThreadExecutor executor) {
    if (context == null || downloadCacheSerializer == null || fileManager == null || executor == null) {
      throw new IllegalArgumentException("Invalid null parameter");
    }
    this.context = context.getApplicationContext();
    this.cacheDir = this.context.getCacheDir();
    this.serializer = downloadCacheSerializer;
    this.fileManager = fileManager;
    this.threadExecutor = executor;
  }

  @Override public Observable<DownloadPayload> get(final String downloadKey) {
    return Observable.create(subscriber -> {
      File downloadEntityFile = DownloadCache.this.buildFile(downloadKey);
      String fileContent = DownloadCache.this.fileManager.readFileContent(downloadEntityFile);
      DownloadPayload downloadPayload = DownloadCache.this.serializer.deserialize(fileContent);

      if (downloadPayload != null) {
        subscriber.onNext(downloadPayload);
        subscriber.onCompleted();
      } else {
        subscriber.onError(new DownloadNotFoundException());
      }
    });
  }

  @Override public void put(DownloadPayload downloadPayload) {
    if (downloadPayload != null) {
      File downloadEntitiyFile = this.buildFile(downloadPayload.getDownloadPayloadKey());
      if (!isCached(downloadPayload.getDownloadPayloadKey())) {
        String jsonString = this.serializer.serialize(downloadPayload);
        this.executeAsynchronously(new CacheWriter(this.fileManager, downloadEntitiyFile,
            jsonString));
        setLastCacheUpdateTimeMillis();
      }
    }
  }

  @Override public boolean isCached(String downloadKey) {
    File downloadEntitiyFile = this.buildFile(downloadKey);
    return this.fileManager.exists(downloadEntitiyFile);
  }

  @Override public boolean isExpired() {
    long currentTime = System.currentTimeMillis();
    long lastUpdateTime = this.getLastCacheUpdateTimeMillis();

    boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);

    if (expired) {
      this.evictAll();
    }

    return expired;
  }

  @Override public void evictAll() {
    this.executeAsynchronously(new CacheEvictor(this.fileManager, this.cacheDir));
  }

  /**
   * Build a file, used to be inserted in the disk cache.
   *
   * @param downloadKey The id download to build the file.
   * @return A valid file.
   */
  private File buildFile(String downloadKey) {
    StringBuilder fileNameBuilder = new StringBuilder();
    fileNameBuilder.append(this.cacheDir.getPath());
    fileNameBuilder.append(File.separator);
    fileNameBuilder.append(DEFAULT_FILE_NAME);
    fileNameBuilder.append(downloadKey);

    return new File(fileNameBuilder.toString());
  }

  /**
   * Set in millis, the last time the cache was accessed.
   */
  private void setLastCacheUpdateTimeMillis() {
    long currentMillis = System.currentTimeMillis();
    this.fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME,
        SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis);
  }

  /**
   * Get in millis, the last time the cache was accessed.
   */
  private long getLastCacheUpdateTimeMillis() {
    return this.fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME,
        SETTINGS_KEY_LAST_CACHE_UPDATE);
  }

  /**
   * Executes a {@link Runnable} in another Thread.
   *
   * @param runnable {@link Runnable} to execute
   */
  private void executeAsynchronously(Runnable runnable) {
    this.threadExecutor.execute(runnable);
  }

  /**
   * {@link Runnable} class for writing to disk.
   */
  private static class CacheWriter implements Runnable {
    private final FileManager fileManager;
    private final File fileToWrite;
    private final String fileContent;

    CacheWriter(FileManager fileManager, File fileToWrite, String fileContent) {
      this.fileManager = fileManager;
      this.fileToWrite = fileToWrite;
      this.fileContent = fileContent;
    }

    @Override public void run() {
      this.fileManager.writeToFile(fileToWrite, fileContent);
    }
  }

  /**
   * {@link Runnable} class for evicting all the cached files
   */
  private static class CacheEvictor implements Runnable {
    private final FileManager fileManager;
    private final File cacheDir;

    CacheEvictor(FileManager fileManager, File cacheDir) {
      this.fileManager = fileManager;
      this.cacheDir = cacheDir;
    }

    @Override public void run() {
      this.fileManager.clearDirectory(this.cacheDir);
    }
  }
}
