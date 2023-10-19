package com.betrybe.agrix.controllers.dto;

import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Farm;

/**
 * CropDto.
 */
public record CropDto(Long id, String name, Double plantedArea) {
  public Crop toCrop() {
    return new Crop(id, name, plantedArea);
  }
}
