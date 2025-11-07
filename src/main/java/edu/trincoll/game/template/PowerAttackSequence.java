
    /**
     * TODO 5c: Implement postAttackAction()
     *
     * Requirements:
     * 1. Attacker is exhausted from power attack
     * 2. Take 10% of max health as recoil damage
     * 3. Use attacker.setHealth() to apply recoil directly
     *    (Can't use takeDamage as it applies defense)
     */
    @Override
    protected void postAttackAction() {
        int recoil = (int) (attacker.getStats().maxHealth() * 0.1);
        attacker.setHealth(attacker.getStats().health() - recoil);
    }
}
