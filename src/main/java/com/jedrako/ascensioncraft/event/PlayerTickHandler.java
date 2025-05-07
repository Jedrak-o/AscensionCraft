        // --- The Mana Regen Logic ---
        float currentMana = status.getCurrentMana();
        float maxMana = status.getMaxMana();

        // Only regenerate if mana isn't already full
        if (currentMana < maxMana) {
            // Define regeneration rates
            final float BASE_REGEN_PER_TICK = 0.025f; // VERY SLOW (0.5 mana/sec) - ADJUST AS NEEDED!
            final float SPIRE_REGEN_MULTIPLIER = 40.0f; // Example: 40x faster inside spires (20 mana/sec) - ADJUST!

            // Check if player is flagged as inside a spire
            boolean inSpire = status.isInsideSpire();

            // Determine the effective regeneration rate for this tick
            float effectiveRate = inSpire ? (BASE_REGEN_PER_TICK * SPIRE_REGEN_MULTIPLIER) : BASE_REGEN_PER_TICK;

            // Get any BONUS mana regen rate from the attunement itself (if we want that)
            // For now, let's assume getManaRegenRate() stored on status IS the bonus rate.
            // If getManaRegenRate() was meant to be the TOTAL rate, we need to rethink.
            // Let's assume it's a BONUS for now:
            float bonusRate = status.getManaRegenRate(); // Should probably be renamed if it's just a bonus?
            // Let's refine: Let's make getManaRegenRate() the BASE rate that can be modified by attunements
            // And have the SPIRE multiplier work on top of THAT.

            // --- REVISED REGEN LOGIC ---
            float baseRateFromStatus = status.getManaRegenRate(); // This is the rate potentially modified by attunement/gear later
                                                                  // Let's set a reasonable default in AttunementStatus implementation:
                                                                  // e.g., DEFAULT_MANA_REGEN = 0.05f (1 mana/sec base)

            // Determine effective rate based on spire location
            effectiveRate = inSpire ? (baseRateFromStatus * SPIRE_REGEN_MULTIPLIER) : baseRateFromStatus;


            // Calculate mana to add
            float manaToAdd = effectiveRate; // Since we run per tick

            // Add the mana. setCurrentMana handles clamping to maxMana.
            status.setCurrentMana(currentMana + manaToAdd);

            // Optional: Debugging print message
            // if (inSpire) System.out.printf("Player %s SPIRE regen: %.3f -> %.2f / %.1f%n", player.getName().getString(), manaToAdd, status.getCurrentMana(), maxMana);
            // else if (manaToAdd > 0) System.out.printf("Player %s normal regen: %.3f -> %.2f / %.1f%n", player.getName().getString(), manaToAdd, status.getCurrentMana(), maxMana);

        } // End if currentMana < maxMana