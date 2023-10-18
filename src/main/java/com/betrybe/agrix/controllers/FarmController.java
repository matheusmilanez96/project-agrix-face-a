package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.CropDto;
import com.betrybe.agrix.controllers.dto.FarmDto;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Farm;
import com.betrybe.agrix.services.FarmService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * FarmController.
 */
@RestController
@RequestMapping(value = "/farms")
public class FarmController {
  private final FarmService farmService;

  @Autowired
  public FarmController(FarmService farmService) {
    this.farmService = farmService;
  }

  @PostMapping()
  public ResponseEntity<Farm> createFarm(@RequestBody FarmDto farmDto) {
    Farm newFarm = farmService.insertFarm(farmDto.toFarm());
    return ResponseEntity.status(HttpStatus.CREATED).body(newFarm);
  }

  /**
   * Método insertCrop.
   */
  @PostMapping("/{farmId}/crops")
  public ResponseEntity<?> insertCrop(@RequestBody CropDto cropDto, @PathVariable Long farmId) {
    Optional<Crop> optionalCrop = farmService.insertCrop(cropDto.toCrop(), farmId);

    if (optionalCrop.isEmpty()) {
      String message = "Fazenda não encontrada!";
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(optionalCrop);
  }

  /**
   * Método getFarmById.
   */
  @GetMapping("/{farmId}")
  public ResponseEntity<?> getFarmById(@PathVariable Long farmId) {
    Optional<Farm> optionalFarm = farmService.getFarmById(farmId);

    if (optionalFarm.isEmpty()) {
      String message = "Fazenda não encontrada!";
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    return ResponseEntity.ok(optionalFarm.get());
  }

  /**
   * Método getAllFarms.
   */
  @GetMapping
  public List<FarmDto> getAllFarms() {
    List<Farm> allFarms = farmService.getAllFarms();
    return allFarms.stream()
        .map((farm) -> new FarmDto(farm.getId(), farm.getName(), farm.getSize()))
        .collect(Collectors.toList());
  }
}
