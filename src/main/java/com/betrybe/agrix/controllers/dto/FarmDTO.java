package com.betrybe.agrix.controllers.dto;

import com.betrybe.agrix.models.entities.Farm;

/**
 * FarmDTO.
 */
public record FarmDTO(Long id, String name, Double size) {

  public Farm toFarm() {
    return new Farm(id, name, size);
  }

}
