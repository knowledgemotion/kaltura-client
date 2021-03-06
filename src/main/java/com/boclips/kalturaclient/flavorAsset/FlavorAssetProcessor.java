package com.boclips.kalturaclient.flavorAsset;

import com.boclips.kalturaclient.flavorAsset.resources.FlavorAssetListResource;
import com.boclips.kalturaclient.flavorAsset.resources.FlavorAssetResource;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FlavorAssetProcessor {

    public List<Asset> processFlavorAssetListResource(FlavorAssetListResource listResource) {
        return listResource.objects.stream()
                .filter(Objects::nonNull)
                .map(this::processFlavorAssetResource)
                .collect(Collectors.toList());
    }

    private Asset processFlavorAssetResource(FlavorAssetResource assetResource) {
        return Asset.builder()
                .id(assetResource.getId())
                .entryId(assetResource.getEntryId())
                .flavorParamsId(assetResource.getFlavorParamsId())
                .sizeKb(assetResource.getSize())
                .bitrateKbps(assetResource.getBitrate())
                .width(assetResource.getWidth())
                .height(assetResource.getHeight())
                .isOriginal(assetResource.getIsOriginal())
                .createdAt(ZonedDateTime.ofInstant(Instant.ofEpochSecond(assetResource.getCreatedAt()), ZoneOffset.UTC))
                .build();
    }
}
