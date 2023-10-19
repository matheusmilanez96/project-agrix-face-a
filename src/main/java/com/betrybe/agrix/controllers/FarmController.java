package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.CropDto;
import com.betrybe.agrix.controllers.dto.FarmDto;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Farm;
import com.betrybe.agrix.services.FarmService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    Optional<Farm> optionalFarm = farmService.getFarmById(farmId);

    if (optionalFarm.isEmpty()) {
      String message = "Fazenda não encontrada!";
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    Crop crop = farmService.insertCrop(cropDto.toCrop());
    Map<String, Object> finalMap = new HashMap<>();
    finalMap.put("id", crop.getId());
    finalMap.put("name", crop.getName());
    finalMap.put("plantedArea", crop.getPlantedArea());
    finalMap.put("farmId", farmId);


    return ResponseEntity.status(HttpStatus.CREATED).body(finalMap);
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
   * Método getCropById.
   */
  @GetMapping("/{farmId}/crops")
  public ResponseEntity<?> getCropsById(@PathVariable Long farmId) {
    Optional<Farm> optionalFarm = farmService.getFarmById(farmId);

    if (optionalFarm.isEmpty()) {
      String message = "Fazenda não encontrada!";
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    List<Crop> crops = farmService.getCropsById(optionalFarm.get());
    crops.forEach(crop -> crop.setFarm(optionalFarm.get()));

    return ResponseEntity.ok(crops);
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
